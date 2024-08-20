package com.globant.trainingnewgen.domain.client.dto;

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
