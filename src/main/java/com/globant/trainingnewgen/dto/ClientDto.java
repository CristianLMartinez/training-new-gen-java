package com.globant.trainingnewgen.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ClientDto(

        @NotNull(message = "Document can't be null")
        String document,

        String name,

        String email,

        String phone,

        String deliveryAddress
) {

}
