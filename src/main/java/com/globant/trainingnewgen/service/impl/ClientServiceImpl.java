package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.mapper.ClientMapper;
import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.repository.OrderRepository;
import com.globant.trainingnewgen.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends BaseService<Client, ClientDto> implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public ClientDto create(ClientDto clientDto) {

        var existingClient = clientRepository.findClientByDocument(clientDto.document());

        validateAndThrowIfExists(
                existingClient.filter(client -> !client.isDeleted()),
                ExceptionCode.USER_ALREADY_EXISTS,
                String.format("User with document %s already exists", clientDto.document())
        );

        if (existingClient.isPresent() && existingClient.get().isDeleted()) {
            existingClient.get().setDeleted(false);
            return ClientMapper.entityToDto(clientRepository.save(existingClient.get()));
        }

        var newClientEntity = clientRepository.save(ClientMapper.dtoToEntity(clientDto));
        return ClientMapper.entityToDto(newClientEntity);
    }


    @Override
    public ClientDto getClientByDocument(String document, Boolean isDeleted) {
        var entity = validateAndRetrieveClientByDocument(document, isDeleted);
        return ClientMapper.entityToDto(entity);
    }


    @Transactional
    @Override
    public void updateClient(String document, ClientDto requestBody) {
        Client existingClient = validateAndRetrieveClientByDocument(document, false);

        if (hasChanges(existingClient, requestBody)) {
            throw new EntityConflictException("No fields were updated.", ExceptionCode.NO_CHANGES);
        }

        existingClient.setName(requestBody.name());
        existingClient.setEmail(requestBody.email());
        existingClient.setPhone(requestBody.phone());
        existingClient.setDeliveryAddress(requestBody.deliveryAddress());

        clientRepository.save(existingClient);
    }


    @Transactional
    @Override
    public void deleteClient(String document) {
        var client = validateAndRetrieveClientByDocument(document, null);
        client.getOrders().clear();
        client.setDeleted(true);
        clientRepository.save(client);
    }


    @Transactional
    @Override
    public void restoreClient(String document) {
        Client client = validateAndRetrieveClientByDocument(document, true);
        client.setDeleted(false);
        clientRepository.save(client);
    }

    /**
     * @param orderBy:   default DOCUMENT, NAME, ADDRESS
     * @param direction: ASC || DESC
     * @return clients
     */
    @Override
    public List<ClientDto> getClients(String orderBy, String direction) {
        var modifiedDirection = Sort.Direction.fromString(direction);
        var sort = Sort.by(modifiedDirection, mapOrderByToField(orderBy));

        var clients = clientRepository.findAll(sort);

        return clients.stream()
                .filter(client -> !client.isDeleted())
                .map(ClientMapper::entityToDto)
                .toList();
    }

    private String mapOrderByToField(String orderBy) {
        return switch (orderBy.toUpperCase()) {
            case "NAME" -> "name";
            case "ADDRESS" -> "deliveryAddress";
            default -> "document";
        };
    }

    protected Client validateAndRetrieveClientByDocument(String document, Boolean isDeleted) {
        return clientRepository.findClientByDocument(document, isDeleted)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Can't find user with document %s", document),
                        ExceptionCode.CLIENT_NOT_FOUND));
    }

    @Override
    protected boolean hasChanges(Client existingClient, ClientDto requestBody) {
        return existingClient.getName().equals(requestBody.name()) &&
                existingClient.getEmail().equals(requestBody.email()) &&
                existingClient.getPhone().equals(requestBody.phone()) &&
                existingClient.getDeliveryAddress().equals(requestBody.deliveryAddress());
    }

}
