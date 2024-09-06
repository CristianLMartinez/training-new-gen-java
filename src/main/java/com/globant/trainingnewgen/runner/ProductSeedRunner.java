package com.globant.trainingnewgen.runner;

import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.model.entity.ProductCategory;
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
public class ProductSeedRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {

        Product product = Product.builder()
                .uuid(UUID.fromString("238f3d59-c10d-4745-913d-8f9e0d36521e"))
                .fantasyName("CHEESE BURGER BIG COMBO")
                .category(ProductCategory.HAMBURGERS_AND_HOT_DOGS)
                .description("Burger double 8 Oz meet, cheese, french fries and Big sogda")
                .price(BigDecimal.valueOf(21008.41))
                .available(true)
                .build();
        productRepository.save(product);

        Product product10 = Product.builder()
                .uuid(UUID.fromString("238f3d59-c10d-4745-913d-8f9e0d36525e"))
                .fantasyName("MEAT BURGER SMALL COMBO")
                .category(ProductCategory.HAMBURGERS_AND_HOT_DOGS)
                .description("Burger double 8 Oz meet, cheese, french fries and Big sogda")
                .price(BigDecimal.valueOf(21008.41))
                .available(true)
                .build();
        productRepository.save(product10);

        Product product1 = Product.builder()
                .uuid(UUID.fromString("f3a2b4c6-8d9e-4f5a-abc1-23f6e7d8a9b0"))
                .fantasyName("PIZZA SUPREME")
                .category(ProductCategory.CHICKEN)
                .description("A mouthwatering pizza topped with a variety of ingredients")
                .price(BigDecimal.valueOf(189.50))
                .available(true)
                .build();
        productRepository.save(product1);

        Product product2 = Product.builder()
                .uuid(UUID.fromString("a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d"))
                .fantasyName("GRILLED SALMON")
                .category(ProductCategory.FISH)
                .description("Freshly grilled salmon with a side of vegetables")
                .price(BigDecimal.valueOf(250.00))
                .available(true)
                .build();
        productRepository.save(product2);

        Product product3 = Product.builder()
                .uuid(UUID.fromString("b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e"))
                .fantasyName("STEAK DINNER")
                .category(ProductCategory.MEATS)
                .description("Juicy steak with mashed potatoes and gravy")
                .price(BigDecimal.valueOf(300.00))
                .available(true)
                .build();
        productRepository.save(product3);

        Product product4 = Product.builder()
                .uuid(UUID.fromString("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f"))
                .fantasyName("CHOCOLATE CAKE")
                .category(ProductCategory.DESSERTS)
                .description("Rich chocolate cake with a creamy frosting")
                .price(BigDecimal.valueOf(50.00))
                .available(true)
                .build();
        productRepository.save(product4);


    }

}
