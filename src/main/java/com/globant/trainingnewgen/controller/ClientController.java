package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.service.ClientService;
import com.globant.trainingnewgen.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Clients rest API", description = "Endpoints to manage clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;
    private final OrderService orderService;
    private final static Logger logger = LoggerFactory.getLogger(ClientController.class);


    @Operation(summary = "Create a new client",
            description = "Creates a new client with the provided details.")
    @PostMapping
    ResponseEntity<ClientDto> saveClient(@RequestBody @Valid ClientDto clientDto) {
        logger.info("Creating client: {}", clientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.create(clientDto));
    }


    @Operation(summary = "Get a client by document",
            description = "Retrieves a client by their document identifier.")
    @GetMapping(value = "{document}")
    ResponseEntity<ClientDto> getClient(
            @PathVariable String document,
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted) {
        logger.info("Getting client with document: {}", document);
        isDeleted = isDeleted != null && isDeleted;
        return ResponseEntity.ok(clientService.getClientByDocument(document, isDeleted));
    }


    @Operation(summary = "Get a list of clients",
            description = "Retrieves a list of clients ordered by the specified field and direction.")
    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients(
            @RequestParam(defaultValue = "DOCUMENT") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        logger.info("Getting clients by order: {} and direction: {}", orderBy, direction);
        List<ClientDto> clients = clientService.getClients(orderBy, direction);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("{document}/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByClientDocument(
            @PathVariable String document) {
        logger.info("Getting orders by client document: {}", document);
        List<OrderDto> orders = orderService.getOrdersByClientDocument(document);
        return ResponseEntity.ok(orders);
    }


    @Operation(summary = "Update a client by document",
            description = "Updates the details of a client identified by their document.")
    @PutMapping("{document}")
    ResponseEntity<Void> updateClient(@PathVariable String document,
                                      @RequestBody @Valid ClientDto clientDto) {
        logger.info("Updating client with document: {}", document);
        clientService.updateClient(document, clientDto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Delete a client by document",
            description = "Deletes a client identified by their document.")
    @DeleteMapping("{document}")
    ResponseEntity<Void> deleteClient(@PathVariable String document) {
        logger.info("Deleting client with document: {}", document);
        clientService.deleteClient(document);
        return ResponseEntity.noContent().build();
    }


}