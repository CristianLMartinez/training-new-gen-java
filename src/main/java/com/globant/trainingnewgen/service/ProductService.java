package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.dto.SalesReportDto;

import java.time.LocalDate;
import java.util.UUID;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    ProductDto getProductByUuid(UUID uuid);

    void updateProduct(UUID uuid, ProductDto productDto);

    void deleteProduct(UUID uuid);

    SalesReportDto getSalesReport(LocalDate startDate, LocalDate endDate);

}
