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
                  .price(BigDecimal.valueOf(31234.21))
                  .available(true)
                  .build();

          productRepository.save(product);
    }

}
