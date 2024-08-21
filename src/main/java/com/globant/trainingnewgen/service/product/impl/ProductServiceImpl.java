package com.globant.trainingnewgen.service.product.impl;


import com.globant.trainingnewgen.dto.ProductDto;
import com.globant.trainingnewgen.mapper.ProductMapper;
import com.globant.trainingnewgen.model.Product;
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
        Product product = ProductMapper.dtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.entityToDto(savedProduct);
    }

}

