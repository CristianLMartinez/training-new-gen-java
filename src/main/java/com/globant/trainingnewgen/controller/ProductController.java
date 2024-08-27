package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.dto.ProductDto;
import com.globant.trainingnewgen.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<ProductDto> getProductByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }

    @PutMapping("{uuid}")
    public ResponseEntity updateProduct(@PathVariable UUID uuid, @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(uuid, productDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @DeleteMapping("{uuid}")
    ResponseEntity deleteProduct(@PathVariable UUID uuid) {
        productService.deleteProduct(uuid);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);

    }


}
