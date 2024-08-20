package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.domain.client.dto.ClientDto;
import com.globant.trainingnewgen.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto clientDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.createClient(clientDto));
    }

    @GetMapping("{document}")
    ResponseEntity<ClientDto> getClient(@PathVariable String document) {
        return ResponseEntity.ok(clientService.getClientByDocument(document));
    }

    @PutMapping("{document}")
    ResponseEntity updateClient(@PathVariable String document, @RequestBody ClientDto clientDto) {
        clientService.updateClient(document, clientDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


    @DeleteMapping("{document}")
    ResponseEntity deleteClientClient(@PathVariable String document) {
        clientService.deleteClient(document);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);

    }

}
