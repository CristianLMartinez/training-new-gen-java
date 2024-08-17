package com.globant.trainingnewgen.dto;

import lombok.Builder;

@Builder
public record ClientDto(
        String document,
        String name,
        String email,
        String phone,
        String deliveryAddress
) {

}
