package com.globant.trainingnewgen.dto;

import com.globant.trainingnewgen.service.client.validation.ValidDocument;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record ClientDto(

        @NotNull(message = "Document can't be null")
        @ValidDocument
        String document,

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Incorrect email format")
        String email,

        @NotBlank(message = "Phone is mandatory")
        @Pattern(regexp = "\\d{3}-\\d{7}", message = "Incorrect phone number format")
        String phone,

        @NotBlank(message = "Address is mandatory")
        @Length(min = 10, max = 500)
        String deliveryAddress
) {

}
