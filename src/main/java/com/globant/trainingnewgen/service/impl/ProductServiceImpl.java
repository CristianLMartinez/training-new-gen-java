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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // todo - refactor this class
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public ProductDto create(ProductDto requestBody) {
        productRepository.findProductByFantasyName(requestBody.fantasyName())
                .ifPresent(product -> {
                    throw new EntityConflictException(String.format("Product with fantasy name %s already exists", requestBody.fantasyName()), ExceptionCode.COMBO_ALREADY_EXISTS);
                });

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


    /**
     * Validate:
     * * if UUID is correct
     * * if exists a product by uuid
     * * If already exists a product with the fantasy name
     * * at least must be a field different
     * @param uuid
     * @param productDto
     */
    @Override
    @Transactional
    public void updateProduct(UUID uuid, ProductDto productDto) {

        var existingProduct = productRepository.findProductByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Product with uuid %s not found", uuid),
                        ExceptionCode.COMBO_NOT_FOUND));


        if (existingProduct.getFantasyName().equals(productDto.fantasyName())) {
            if (!hasChanges(existingProduct, productDto)) {
                throw new EntityConflictException("No fields were updated.", ExceptionCode.NO_CHANGES);
            }
        } else if (productRepository.existsByFantasyName(productDto.fantasyName())) {
            throw new EntityConflictException(String.format("Product with fantasy name %s already used!",
                    productDto.fantasyName()), ExceptionCode.FANTASY_NAME_ALREADY_USED);
        }

        updateExistingProduct(existingProduct, productDto);

        productRepository.save(existingProduct);
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

    private void updateExistingProduct(Product existingProduct, ProductDto productDto) {
        existingProduct.setFantasyName(productDto.fantasyName());
        existingProduct.setCategory(productDto.category());
        existingProduct.setDescription(productDto.description());
        existingProduct.setPrice(productDto.price());
        existingProduct.setAvailable(productDto.available());
    }

    private boolean hasChanges(Product existingProduct, ProductDto productDto) {
        return !existingProduct.getFantasyName().equals(productDto.fantasyName()) ||
                !existingProduct.getCategory().equals(productDto.category()) ||
                !existingProduct.getDescription().equals(productDto.description()) ||
                !existingProduct.getPrice().equals(productDto.price()) ||
                existingProduct.isAvailable() != productDto.available();
    }

    @Override
    @Transactional
    public List<ProductDto> searchProductsByFantasyName(String fantasyName) {
        List<Product> products = productRepository.findByFantasyNameLikeIgnoreCaseOrderByFantasyNameAsc(fantasyName);
        return products.stream()
                .map(ProductMapper::entityToDto)
                .collect(Collectors.toList());
    }


}

