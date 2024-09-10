package com.globant.trainingnewgen.model.dto;

import jakarta.validation.constraints.Digits;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderDto(

        UUID uuid,

        LocalDateTime creationDateTime,

        String clientDocument,

        List<OrderItemsDto> products,

        String extraInformation,

        @Digits(integer = 10, fraction = 2)
        BigDecimal subTotal,

        @Digits(integer = 10, fraction = 2)
        BigDecimal tax,

        @Digits(integer = 10, fraction = 2)
        BigDecimal grandTotal,

        boolean delivered,

        LocalDateTime deliveryDate

) {}
