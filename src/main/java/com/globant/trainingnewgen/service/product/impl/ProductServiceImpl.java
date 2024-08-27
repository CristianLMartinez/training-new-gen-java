package com.globant.trainingnewgen.service.product.impl;


import com.globant.trainingnewgen.dto.ProductDto;
import com.globant.trainingnewgen.exception.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
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
    public ProductDto create(ProductDto requestBody) {
        productRepository.findProductByFantasyName(requestBody.fantasyName())
                .ifPresent(product -> {
                    throw new EntityConflictException(
                            String.format("Product with fantasy name %s already exists", requestBody
                                    .fantasyName()), ExceptionCode.COMBO_ALREADY_EXISTS);});

        var product = ProductMapper.dtoToEntity(requestBody);
        var savedProduct = productRepository.save(product);

        return ProductMapper.entityToDto(savedProduct);
    }

}

