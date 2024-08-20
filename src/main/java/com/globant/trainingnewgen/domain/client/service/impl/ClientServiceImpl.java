package com.globant.trainingnewgen.domain.client.service.impl;

import com.globant.trainingnewgen.domain.client.dto.ClientDto;
import com.globant.trainingnewgen.domain.client.service.ClientService;
import com.globant.trainingnewgen.domain.client.mapper.ClientMapper;
import com.globant.trainingnewgen.domain.client.repository.ClientRepository;
import com.globant.trainingnewgen.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
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
