package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.model.mapper.ProductMapper;
import com.globant.trainingnewgen.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Product Service Test")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDto = ProductDto.builder()
                .uuid(UUID.randomUUID())
                .description("Burger with cheese")
                .available(Boolean.TRUE)
                .category(ProductCategory.HAMBURGERS_AND_HOT_DOGS)
                .fantasyName("Classic Burger")
                .price(BigDecimal.valueOf(1000.02))
                .build();
    }

    @Test
    @DisplayName("Test create product")
    void testCreateProduct() {

        when(productRepository.findProductByFantasyName(anyString())).thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class))).thenReturn(ProductMapper.dtoToEntity(productDto));

        ProductDto createdProduct = productService.create(productDto);

        assertNotNull(createdProduct);
        assertEquals(productDto.fantasyName(), createdProduct.fantasyName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Test create product throws EntityConflictException")
    void testCreateProductThrowsEntityConflictException() {

        when(productRepository.findProductByFantasyName(anyString())).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));
        assertThrows(EntityConflictException.class, () -> productService.create(productDto));

        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test get product by uuid")
    void testGetProductByUuid() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));

        ProductDto foundProduct = productService.getProductByUuid(productDto.uuid());

        assertNotNull(foundProduct);
        assertEquals(productDto.uuid(), foundProduct.uuid());
        verify(productRepository).findProductByUuid(any(UUID.class));
    }

    @Test
    @DisplayName("Test get product by uuid throws ResourceNotFoundException")
    void testGetProductByUuidThrowsResourceNotFoundException() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductByUuid(productDto.uuid()));
        verify(productRepository).findProductByUuid(any(UUID.class));
    }



    @Test
    @DisplayName("Test delete product by uuid")
    void testDeleteProduct() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));

        productService.deleteProduct(productDto.uuid());
        verify(productRepository).save(any());
    }

    @Test
    @DisplayName("Test delete product by uuid throws ResourceNotFoundException")
    void testDeleteProductThrowsResourceNotFoundException() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productDto.uuid()));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test update product")
    void testUpdateProduct() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var updatedProductDto = ProductDto.builder()
                .uuid(productDto.uuid())
                .fantasyName("Burger Master")
                .description(productDto.description())
                .category(productDto.category())
                .price(productDto.price())
                .available(productDto.available())
                .build();

        assertDoesNotThrow(() -> productService.updateProduct(updatedProductDto.uuid(), updatedProductDto));
        verify(productRepository).save(any());
    }

    @Test
    @DisplayName("Test update product throws ResourceNotFoundException")
    void testUpdateProductThrowsResourceNotFoundException() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productDto.uuid(), productDto));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test update product throws EntityConflictException")
    void testUpdateProductThrowsEntityConflictException() {

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));
        when(productRepository.findProductByFantasyName(anyString())).thenReturn(Optional.of(ProductMapper.dtoToEntity(productDto)));

        assertThrows(EntityConflictException.class, () -> productService.updateProduct(productDto.uuid(), productDto));
        verify(productRepository, never()).save(any());
    }
}