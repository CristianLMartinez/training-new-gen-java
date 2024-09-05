package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<ClientDto> saveClient(@RequestBody @Valid ClientDto clientDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.create(clientDto));
    }

    @GetMapping("{document}")
    ResponseEntity<ClientDto> getClient(
            @PathVariable String document,
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted) {

        isDeleted = isDeleted != null && isDeleted;
        return ResponseEntity.ok(clientService.getClientByDocument(document, isDeleted));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients(
            @RequestParam(defaultValue = "DOCUMENT") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        List<ClientDto> clients = clientService.getClients(orderBy, direction);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("{document}")
    ResponseEntity<Void> updateClient(@PathVariable String document,
                                      @RequestBody @Valid ClientDto clientDto) {
        clientService.updateClient(document, clientDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{document}")
    ResponseEntity<Void> deleteClient(@PathVariable String document) {
        clientService.deleteClient(document);
        return ResponseEntity.noContent().build();
    }


}
