package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.dto.CreateOrderDto;
import com.globant.trainingnewgen.dto.OrderDto;
import com.globant.trainingnewgen.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderDto order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(order));
    }

}
