package com.globant.trainingnewgen.service.client.impl;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.exception.ResourceNotFoundException;
import com.globant.trainingnewgen.mapper.ClientMapper;
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
        var clientEntity = clientRepository
                .save(ClientMapper.dtoToEntity(clientDto));

        return ClientMapper.entityToDto(clientEntity);
    }


    @Override
    public ClientDto getClientByDocument(String document) {
        var entity = clientRepository.getClientByDocument(document)
                .orElseThrow(() -> new IllegalArgumentException("Can't find user with document " + document));
        return ClientMapper.entityToDto(entity);
    }

    @Override
    public void updateClient(String document, ClientDto requestBody) {
        clientRepository.getClientByDocument(document)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can't find user with document %s", document)));

        var entity = ClientMapper.dtoToEntity(requestBody);

        clientRepository.save(entity);

    }

    @Override
    public void deleteClient(String document) {
        clientRepository.getClientByDocument(document)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can't find user with document %s", document)));
        clientRepository.deleteByDocument(document);
    }

}