package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
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

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderDto order) {
        logger.info("Creating order {}", order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(order));
    }

    //  patch mapping
    @PatchMapping("{uuid}/delivered/{timestamp}")
    ResponseEntity<OrderDto> deliverOrder(
            @PathVariable("uuid") UUID uuid,
            @PathVariable
            @Future(message = "Validation failed: The provided date and time must be in the future. Please ensure that the 'timestamp' parameter is set to a future date and time.")
            LocalDateTime timestamp
    ) {
        logger.info("Delivering order {}", uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.updateDeliveredState(uuid, timestamp));
    }


}
