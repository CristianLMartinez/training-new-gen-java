package com.globant.trainingnewgen.service.impl;


import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.mapper.ProductMapper;
import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseService<Product, ProductDto> implements ProductService {

    private final ProductRepository productRepository;


    @Override
    @Transactional
    public ProductDto create(ProductDto requestBody) {
        validateAndThrowIfExists(
                productRepository.findProductByFantasyName(requestBody.fantasyName()),
                ExceptionCode.COMBO_ALREADY_EXISTS,
                String.format("Product with fantasy name %s already exists", requestBody.fantasyName())
        );

        var product = ProductMapper.dtoToEntity(requestBody);
        product.setFantasyName(product.getFantasyName().toUpperCase());

        var savedProduct = productRepository.save(product);
        return ProductMapper.entityToDto(savedProduct);
    }


    @Override
    public ProductDto getProductByUuid(UUID uuid) {
        var product = productRepository.findProductByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with uuid %s not found", uuid), ExceptionCode.COMBO_NOT_FOUND));

        return ProductMapper.entityToDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID uuid) {
        var existingProduct = productRepository.findProductByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with uuid %s not found", uuid),
                        ExceptionCode.COMBO_NOT_FOUND));

        existingProduct.setDeleted(true);
        productRepository.save(existingProduct);
    }

    @Override
    public List<ProductDto> searchProductsByFantasyName(String fantasyName) {
        List<Product> products = productRepository.searchByFantasyName(fantasyName);
        return products.stream()
                .map(ProductMapper::entityToDto)
                .toList();
    }


    @Override
    @Transactional
    public void updateProduct(UUID uuid, ProductDto productDto) {
        var existingProduct = retrieveOrThrow(
                productRepository.findProductByUuid(uuid),
                ExceptionCode.COMBO_NOT_FOUND,
                String.format("Product with uuid %s not found", uuid)
        );

        if (existingProduct.getFantasyName().equals(productDto.fantasyName())) {
            if (hasChanges(existingProduct, productDto)) {
                throw new EntityConflictException("No fields were updated.", ExceptionCode.NO_CHANGES);
            }
        } else {
            validateAndThrowIfExists(
                    productRepository.findProductByFantasyName(productDto.fantasyName()),
                    ExceptionCode.FANTASY_NAME_ALREADY_USED,
                    String.format("Product with fantasy name %s already used!", productDto.fantasyName())
            );
        }

        updateExistingProduct(existingProduct, productDto);
        productRepository.save(existingProduct);
    }

    @Override
    protected boolean hasChanges(Product existingProduct, ProductDto productDto) {
        return existingProduct.getFantasyName().equals(productDto.fantasyName()) &&
                existingProduct.getCategory().equals(productDto.category()) &&
                existingProduct.getDescription().equals(productDto.description()) &&
                existingProduct.getPrice().equals(productDto.price()) &&
                existingProduct.isAvailable() == productDto.available();
    }

    private void updateExistingProduct(Product existingProduct, ProductDto productDto) {
        existingProduct.setFantasyName(productDto.fantasyName());
        existingProduct.setCategory(productDto.category());
        existingProduct.setDescription(productDto.description());
        existingProduct.setPrice(productDto.price());
        existingProduct.setAvailable(productDto.available());
    }


}

