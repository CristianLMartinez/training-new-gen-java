package com.globant.trainingnewgen.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateOrderDto(

        @NotNull(message = "Client document can't be null")
        @Pattern(regexp = "^(CE|CC|TI|P)-\\d{1,17}$", message = "Incorrect client document format")
        String clientDocument,

        @NotNull(message = "Product uuid can't be null")
        UUID productUuid,

        @Min(1)
        @Max(99)
        int quantity,

        String extraInformation

) {
}
