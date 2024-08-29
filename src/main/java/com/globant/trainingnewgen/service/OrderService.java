package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.dto.CreateOrderDto;
import com.globant.trainingnewgen.dto.OrderDto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderService {
    OrderDto create(CreateOrderDto createOrderDto);
    OrderDto updateDeliveredState(UUID orderId, LocalDateTime deliveredDate);
}
