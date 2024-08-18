package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.dto.ClientDto;

public interface ClientService{

    ClientDto createClient(ClientDto clientDto);

    ClientDto getClientByDocument(String document);
}
