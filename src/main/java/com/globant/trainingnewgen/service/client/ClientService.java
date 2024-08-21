package com.globant.trainingnewgen.service.client;

import com.globant.trainingnewgen.dto.ClientDto;

public interface ClientService{

    ClientDto create(ClientDto clientDto);

    ClientDto getClientByDocument(String document);

    void updateClient(String document, ClientDto requestBody);

    void deleteClient(String document);

}
