package com.globant.trainingnewgen.domain.client.service;

import com.globant.trainingnewgen.domain.client.dto.ClientDto;

public interface ClientService{

    ClientDto createClient(ClientDto clientDto);

    ClientDto getClientByDocument(String document);

    void updateClient(String document, ClientDto requestBody);

    void deleteClient(String document);

}
