package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.dto.CreateOrderDto;
import com.globant.trainingnewgen.dto.OrderDto;
import com.globant.trainingnewgen.model.Order;

public interface OrderService {
    OrderDto create(CreateOrderDto createOrderDto);
}
