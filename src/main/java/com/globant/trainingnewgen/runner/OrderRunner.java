package com.globant.trainingnewgen.runner;

import com.globant.trainingnewgen.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(3)
@Component
@RequiredArgsConstructor
public class OrderRunner implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {

    }


}
