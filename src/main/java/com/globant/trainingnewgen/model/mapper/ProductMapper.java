package com.globant.trainingnewgen.model.mapper;

import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.entity.Product;


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
