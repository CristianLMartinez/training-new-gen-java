package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @PostMapping
    ResponseEntity<ClientDto> saveClient(@RequestBody @Valid ClientDto clientDto) {
        LOGGER.info("Creating client: {}", clientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.create(clientDto));
    }

    @GetMapping(value = "{document}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    ResponseEntity<ClientDto> getClient(
            @PathVariable String document,
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted) {
        LOGGER.info("Getting client with document: {}", document);
        isDeleted = isDeleted != null && isDeleted;
        return ResponseEntity.ok(clientService.getClientByDocument(document, isDeleted));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients(
            @RequestParam(defaultValue = "DOCUMENT") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        LOGGER.info("Getting clients by order: {} and direction: {}", orderBy, direction);
        List<ClientDto> clients = clientService.getClients(orderBy, direction);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("{document}")
    ResponseEntity<Void> updateClient(@PathVariable String document,
                                      @RequestBody @Valid ClientDto clientDto) {
        LOGGER.info("Updating client with document: {}", document);
        clientService.updateClient(document, clientDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{document}")
    ResponseEntity<Void> deleteClient(@PathVariable String document) {
        LOGGER.info("Deleting client with document: {}", document);
        clientService.deleteClient(document);
        return ResponseEntity.noContent().build();
    }


}
