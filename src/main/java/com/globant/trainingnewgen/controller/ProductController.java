package com.globant.trainingnewgen.controller;


import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.dto.SalesReportDto;
import com.globant.trainingnewgen.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @GetMapping("/sales_report/{date1}/{date2}")
    public ResponseEntity<?> getSalesReport(
            @PathVariable String date1,
            @PathVariable String date2) {


        if (!isValidDate(date1) || !isValidDate(date2)) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYYMMDD.");
        }

        LocalDate startDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate endDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (!startDate.isBefore(endDate)) {
            return ResponseEntity.badRequest().body("The start date must be before the end date.");
        }

        SalesReportDto report = productService.getSalesReport(startDate, endDate);

        return ResponseEntity.ok(report);
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}
