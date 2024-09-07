package com.globant.trainingnewgen.controller;

import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.dto.SalesReportDto;
import com.globant.trainingnewgen.service.ProductService;
import com.globant.trainingnewgen.service.SalesReportService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SalesReportService salesReportService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Creating product {}", productDto);
        ProductDto createdProduct = productService.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<ProductDto> getProductByUuid(@PathVariable UUID uuid) {
        logger.info("Retrieving product {}", uuid);
        return ResponseEntity.ok(productService.getProductByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateProduct(@PathVariable UUID uuid, @Valid @RequestBody ProductDto productDto) {
        logger.info("Updating product {}", productDto);
        productService.updateProduct(uuid, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID uuid) {
        logger.info("Deleting product {}", uuid);
        productService.deleteProduct(uuid);
        return ResponseEntity.noContent().build();
    }

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


    @GetMapping("/sales_report/{startDate}/{endDate}")
    public ResponseEntity<SalesReportDto> getSalesReport(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        var report = salesReportService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(report);

    }


}
