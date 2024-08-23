package com.globant.trainingnewgen.service.client.impl;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.mapper.ClientMapper;
import com.globant.trainingnewgen.model.Client;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDto create(ClientDto clientDto) {
        clientRepository.findClientByDocument(clientDto.document())
                .ifPresent(client -> {
                    throw new EntityConflictException(
                            String.format("User with document %s already exists", clientDto.document()),
                            ExceptionCode.USER_ALREADY_EXISTS);
                });

        var clientEntity = clientRepository
                .save(ClientMapper.dtoToEntity(clientDto));

        return ClientMapper.entityToDto(clientEntity);
    }


    @Override
    public ClientDto getClientByDocument(String document) {
        var entity = validateAndRetrieveClientByDocument(document);
        return ClientMapper.entityToDto(entity);
    }

    @Override
    public void updateClient(String document, ClientDto requestBody) {
        // todo - validations for update
        validateAndRetrieveClientByDocument(document);
        var entity = ClientMapper.dtoToEntity(requestBody);
        clientRepository.save(entity);
    }

    @Override
    public void deleteClient(String document) {
        var client = validateAndRetrieveClientByDocument(document);
        clientRepository.deleteByDocument(client.getDocument());
    }


    private Client validateAndRetrieveClientByDocument(String document) {
        return clientRepository.findClientByDocument(document)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Can't find user with document %s", document),
                        ExceptionCode.CLIENT_NOT_FOUND));
    }




}