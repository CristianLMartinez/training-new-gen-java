package com.globant.trainingnewgen.runner;

import com.globant.trainingnewgen.model.Product;
import com.globant.trainingnewgen.model.ProductCategory;
import com.globant.trainingnewgen.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Order(2)
@Component
@RequiredArgsConstructor
public class ProductRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
          Product product = Product.builder()
                  .uuid(UUID.fromString("238f3d59-c10d-4745-913d-8f9e0d36521e"))
                  .fantasyName("CHEESE BURGER BIG COMBO")
                  .category(ProductCategory.HAMBURGERS_AND_HOTDOGS)
                  .description("Burger double 8 Oz meet, cheese, french fries and Big sogda")
                  .price(BigDecimal.valueOf(21008.41))
                  .available(true)
                  .build();

          productRepository.save(product);



        Product product1 = Product.builder()
                .uuid(UUID.fromString("f3a2b4c6-8d9e-4f5a-abc1-23f6e7d8a9b0"))
                .fantasyName("PIZZA SUPREME")
                .category(ProductCategory.CHICKEN)
                .description("A mouthwatering pizza topped with a variety of ingredients")
                .price(BigDecimal.valueOf(189.50))
                .available(true)
                .build();

        productRepository.save(product1);


    }

}
