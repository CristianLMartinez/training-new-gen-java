package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(CreateOrderDto createOrderDto);
    OrderDto updateDeliveredState(UUID orderId, LocalDateTime deliveredDate);
    List<OrderDto> getOrdersByClientDocument(String document);
}
