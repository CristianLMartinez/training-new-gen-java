package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.dto.SalesReportDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    List<ProductDto> getAll();

    ProductDto getProductByUuid(UUID uuid);

    void updateProduct(UUID uuid, ProductDto productDto);

    void deleteProduct(UUID uuid);

    List<ProductDto> searchProductsByFantasyName(String fantasyName);

}
