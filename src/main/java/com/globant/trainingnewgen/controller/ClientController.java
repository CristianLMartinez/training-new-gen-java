package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.service.ClientService;
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


}
