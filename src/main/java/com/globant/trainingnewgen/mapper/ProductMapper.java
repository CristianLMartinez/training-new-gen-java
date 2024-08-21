package com.globant.trainingnewgen.mapper;

import com.globant.trainingnewgen.dto.ProductDto;
import com.globant.trainingnewgen.model.Product;


public class ProductMapper {

    private ProductMapper() {
        throw new IllegalArgumentException("Utility class");
    }

    public static ProductDto entityToDto(Product product) {
        return ProductDto.builder()
                .uuid(product.getUuid())
                .fantasyName(product.getFantasyName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.isAvailable())
                .build();
    }

    public static Product dtoToEntity(ProductDto dto) {
        return Product.builder()
                .uuid(dto.uuid())
                .fantasyName(dto.fantasyName())
                .category(dto.category())
                .description(dto.description())
                .price(dto.price())
                .available(dto.available())
                .build();
    }
}
