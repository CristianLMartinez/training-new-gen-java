package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.map.ClientMapper;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.service.ClientService;
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

}
