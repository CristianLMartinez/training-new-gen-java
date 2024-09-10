package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name = "Products rest API", description = "Endpoints to manage products")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Operation(summary = "Create a product", description = "Creates a new product with the provided details.")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Creating product {}", productDto);
        ProductDto createdProduct = productService.create(productDto);
        return ResponseEntity.ok(createdProduct);
    }
    @Operation(summary = "Get a product by UUID", description = "Retrieves a product by its UUID.")
    @GetMapping("{uuid}")
    public ResponseEntity<ProductDto> getProductByUuid(@PathVariable UUID uuid) {
        logger.info("Retrieving product {}", uuid);
        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }
    @Operation(summary = "Update a product", description = "Updates an existing product by UUID with the provided details.")
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateProduct(@PathVariable UUID uuid, @Valid @RequestBody ProductDto productDto) {
        logger.info("Updating product {}", productDto);
        productService.updateProduct(uuid, productDto);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Delete a product", description = "Deletes a product by its UUID.")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID uuid) {
        logger.info("Deleting product {}", uuid);
        productService.deleteProduct(uuid);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Search products by name", description = "Searches for products by their fantasy name.")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByFantasyName(
            @RequestParam(value = "q", required = true) String q) {
        logger.info("Searching products by name {}", q);

        if (q == null || q.trim().isEmpty()) {
            throw new IllegalArgumentException("Query parameter 'q' is required");
        }

        List<ProductDto> products = productService.searchProductsByFantasyName(q);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(products);
    }

}
