package com.globant.trainingnewgen.dto;

import com.globant.trainingnewgen.service.client.validation.ValidDocument;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ClientDto(

        @NotNull(message = "Document can't be null")
        @ValidDocument
        String document,

        String name,

        @Email
        String email,

        String phone,

        String deliveryAddress
) {

}
