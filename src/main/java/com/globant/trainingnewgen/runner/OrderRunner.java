package com.globant.trainingnewgen.runner;

import com.globant.trainingnewgen.dto.CreateOrderDto;
import com.globant.trainingnewgen.mapper.OrderMapper;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.repository.OrderRepository;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Order(3)
@Component
@RequiredArgsConstructor
public class OrderRunner implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        var createOrderDto = CreateOrderDto.builder()
                .quantity(2)
                .clientDocument("CC-123456789")
                .extraInformation("Hamburguer without letucce nor ketchup")
                .productUuid(UUID.fromString("238f3d59-c10d-4745-913d-8f9e0d36521e"))
                .build();

        var order = OrderMapper.createOrderDtotoEntity(createOrderDto);

        var product = productRepository.findById(1L).orElseThrow();
        var client = clientRepository.findById(1L).orElseThrow();

        order.setProduct(product);
        order.setClient(client);
        order.setUuid(UUID.fromString("238f3d59-c10d-4745-913d-8f9e0d36532b"));

        calculateTotals(order, product.getPrice());
        orderRepository.save(order);


    }

    private void calculateTotals(com.globant.trainingnewgen.model.Order order, BigDecimal productPrice) {
        BigDecimal subTotal = productPrice.multiply(BigDecimal.valueOf(order.getQuantity()));
        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal grandTotal = subTotal.add(tax);

        order.setSubTotal(subTotal);
        order.setTax(tax);
        order.setGrandTotal(grandTotal);
    }



}
