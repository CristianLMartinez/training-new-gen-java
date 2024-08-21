package com.globant.trainingnewgen.service.product.impl;


import com.globant.trainingnewgen.dto.ProductDto;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public ProductDto create(ProductDto productDto) {
        return null;
    }

}
