package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.mapper.ClientMapper;
import com.globant.trainingnewgen.model.Client;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public ClientDto create(ClientDto clientDto) {
        var existingClient = clientRepository.findClientByDocument(clientDto.document(), true);

        if (existingClient.isPresent()) {
            var client = existingClient.get();
            if (client.isDeleted()) {
                // Restore the client
                client.setDeleted(false);
                return ClientMapper.entityToDto(clientRepository.save(client));
            } else {
                throw new EntityConflictException(String.format("User with document %s already exists", clientDto.document()), ExceptionCode.USER_ALREADY_EXISTS);
            }
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

        if (!hasChanges(existingClient, requestBody)) {
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
        Client client = validateAndRetrieveClientByDocument(document, false);
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
     * @return
     */
    @Override
    public List<ClientDto> getClients(String orderBy, String direction) {
        // https://www.baeldung.com/spring-data-sorting - sort examples
        var modifiedDirection = Sort.Direction.fromString(direction);
        var sort = Sort.by(modifiedDirection, mapOrderByToField(orderBy));

        var clients = clientRepository.findAll(sort);

        return clients.stream()
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

    private Client validateAndRetrieveClientByDocument(String document, boolean isDeleted) {
        return clientRepository.findClientByDocument(document, isDeleted)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Can't find user with document %s", document),
                        ExceptionCode.CLIENT_NOT_FOUND));
    }

    private boolean hasChanges(Client existingClient, ClientDto requestBody) {
        return !existingClient.getName().equals(requestBody.name()) ||
                !existingClient.getEmail().equals(requestBody.email()) ||
                !existingClient.getPhone().equals(requestBody.phone()) ||
                !existingClient.getDeliveryAddress().equals(requestBody.deliveryAddress());
    }

}
