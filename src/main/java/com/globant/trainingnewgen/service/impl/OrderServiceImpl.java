package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.model.dto.OrderItemsDto;
import com.globant.trainingnewgen.model.entity.OrderItems;
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
import java.util.ArrayList;
import java.util.List;
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

        var client = clientRepository.findClientByDocument(orderDto.clientDocument(), false)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Client with document: %s not found", orderDto.clientDocument()),
                        ExceptionCode.CLIENT_NOT_FOUND));

        var order = orderDto.toEntity();
        order.setClient(client);

        // Procesar los productos
        List<OrderItems> orderProducts = new ArrayList<>();
        for (OrderItemsDto productDto : orderDto.products()) {
            var product = productRepository.findProductByUuid(productDto.productUuid())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Product with uuid: %s not found", productDto.productUuid())));

            var orderProduct = OrderItems.builder()
                    .order(order)
                    .product(product)
                    .quantity(productDto.quantity())
                    .build();

            calculateTotals(order, product.getPrice(), productDto.quantity());

            orderProducts.add(orderProduct);
        }

        order.setOrderItems(orderProducts);
        order = orderRepository.save(order);
        return OrderMapper.entityToOrderDto(order);
    }


    @Override
    public OrderDto updateDeliveredState(UUID orderUuid, LocalDateTime deliveredDate) {
        var order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with uuid: %s not found", orderUuid)));

        if(deliveredDate.isBefore(order.getCreationDateTime())){
            throw new IllegalArgumentException(String.format("The date: %s is before to the current date", deliveredDate));
        }
        order.setDeliveryDate(deliveredDate);
        order.setDelivered(Boolean.TRUE);

        var orderSaved = orderRepository.save(order);
        return OrderMapper.entityToOrderDto(orderSaved);
    }

    @Override
    public List<OrderDto> getOrdersByClientDocument(String document) {
        var client = clientRepository.findClientByDocument(document, false)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Client with uuid: %s not found", document), ExceptionCode.CLIENT_NOT_FOUND));
        var orders = orderRepository.findByClientDocument(client.getDocument());
        return orders.stream().map(OrderMapper::entityToOrderDto).toList();
    }


    private void calculateTotals(Order order, BigDecimal productPrice, int quantity) {
        BigDecimal subTotal = productPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal grandTotal = subTotal.add(tax);

        order.setSubTotal(subTotal);
        order.setTax(tax);
        order.setGrandTotal(grandTotal);
    }

}