package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.repository.ProductRepository;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@DisplayName("Product Service Test")
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test create a new product")
    public void testCreateProduct() {

        UUID productId = UUID.randomUUID();
        String fantasyName = "Sample Product";
        ProductDto productDto = new ProductDto(productId, fantasyName, null, "Description", BigDecimal.valueOf(100), true);

        Product product = new Product();
        product.setId(1L);
        product.setFantasyName("Sample Product");

        when(productRepository.findProductByFantasyName(fantasyName)).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.create(productDto);

        assertNotNull(result);
        assertEquals("Sample Product", result.fantasyName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Test get product by uuid")
    public void testGetProductByUuid() {

        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setUuid(uuid);

        when(productRepository.findProductByUuid(uuid)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductByUuid(uuid);

        assertNotNull(result);
        assertEquals(uuid, result.uuid());
        verify(productRepository, times(1)).findProductByUuid(uuid);
    }

    @Test
    @DisplayName("Test get product by uuid not found")
    public void testGetProductByUuid_NotFound() {

        UUID uuid = UUID.randomUUID();
        when(productRepository.findProductByUuid(uuid)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductByUuid(uuid);
        });

        verify(productRepository, times(1)).findProductByUuid(uuid);
    }

    @Test
    @DisplayName("Test delete product by uuid")
    public void testDeleteProduct() {

        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setId(1L);
        product.setUuid(uuid);
        product.setDeleted(false);

        when(productRepository.findProductByUuid(uuid)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.deleteProduct(uuid);

        assertTrue(product.isDeleted());
        verify(productRepository, times(1)).findProductByUuid(uuid);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Test update product by uuid")
    public void testUpdateProduct() {

        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setId(1L);
        product.setUuid(uuid);
        product.setFantasyName("Hot-dog");

        ProductDto productDto = new ProductDto(uuid, "Updated Product", null, "without onion", BigDecimal.valueOf(200), false);

        when(productRepository.findProductByUuid(uuid)).thenReturn(Optional.of(product));

        productService.updateProduct(uuid, productDto);

        verify(productRepository, times(1)).save(product);
    }
}