package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@Tag(name = "Orders rest API", description = "Endpoints to manage orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Operation(summary = "Create an order", description = "Creates a new order with the provided details.")
    @PostMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderDto order) {
        logger.info("Creating order {}", order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.create(order));
    }

    //  patch mapping
    @Operation(summary = "Mark an order as delivered", description = "Updates the delivery timestamp of an order.")
    @PatchMapping("{uuid}/delivered/{timestamp}")
    ResponseEntity<OrderDto> updateDeliveredState(
            @PathVariable("uuid") UUID uuid,
            @PathVariable LocalDateTime timestamp
    ) {
        logger.info("Delivering order {}", uuid);
        return ResponseEntity.ok(orderService.updateDeliveredState(uuid, timestamp));
    }


}
