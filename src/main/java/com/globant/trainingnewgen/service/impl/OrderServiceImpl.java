package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.exception.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.model.mapper.OrderMapper;
import com.globant.trainingnewgen.model.entity.Order;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.repository.OrderRepository;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public OrderDto create(CreateOrderDto orderDto) {

        var product = productRepository.findProductByUuid(orderDto.productUuid())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with uuid: %s not found", orderDto.productUuid())));

        var client = clientRepository.findClientByDocument(orderDto.clientDocument(), false)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Client with uuid: %s not found", orderDto.clientDocument()), ExceptionCode.CLIENT_NOT_FOUND));

        if(client.isDeleted()){
            throw new ResourceNotFoundException(String.format("Client with uuid: %s not found", orderDto.clientDocument()), ExceptionCode.CLIENT_NOT_FOUND);
        }

        var order = orderDto.toEntity();

        calculateTotals(order, product.getPrice());

        order.setProduct(product);
        order.setClient(client);
        order = orderRepository.save(order);

        return OrderMapper.entityToOrderDto(order);
    }

    @Override
    public OrderDto updateDeliveredState(UUID orderUuid, LocalDateTime deliveredDate) {
        var order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with uuid: %s not found", orderUuid)));

        order.setDeliveryDate(deliveredDate);
        order.setDelivered(Boolean.TRUE);

        var orderSaved = orderRepository.save(order);
        return  OrderMapper.entityToOrderDto(orderSaved);
    }

    private void calculateTotals(Order order, BigDecimal productPrice) {
        BigDecimal subTotal = productPrice.multiply(BigDecimal.valueOf(order.getQuantity()));
        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal grandTotal = subTotal.add(tax);

        order.setSubTotal(subTotal);
        order.setTax(tax);
        order.setGrandTotal(grandTotal);
    }

}