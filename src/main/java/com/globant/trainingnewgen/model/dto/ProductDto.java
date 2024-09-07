package com.globant.trainingnewgen.model.dto;

import com.globant.trainingnewgen.model.entity.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductDto(
        UUID uuid,

        @NotBlank
        @Size(max = 255)
        String fantasyName,

        @NotNull
        ProductCategory category,

        @NotBlank
        @Size(max = 511)
        String description,

        @NotNull
        @DecimalMin(value = "0.01", inclusive = true)
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        boolean available
) {}
