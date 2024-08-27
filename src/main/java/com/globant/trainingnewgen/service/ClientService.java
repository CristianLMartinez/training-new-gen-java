package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto create(ClientDto clientDto);

    ClientDto getClientByDocument(String document, Boolean isDeleted);

    void updateClient(String document, ClientDto requestBody);

    void deleteClient(String document);

    void restoreClient(String document);

    List<ClientDto> getClients(String orderBy, String direction);
}
