package com.globant.trainingnewgen.model.dto;

import com.globant.trainingnewgen.model.entity.OrderStatus;
import jakarta.validation.constraints.Digits;
import lombok.Builder;

import java.util.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderDto(

        UUID uuid,

        LocalDateTime creationDateTime,

        String clientDocument,

        UUID productUuid,

        int quantity,

        String extraInformation,

        @Digits(integer = 10, fraction = 2)
        BigDecimal subTotal,

        @Digits(integer = 10, fraction = 2)
        BigDecimal tax,

        @Digits(integer = 10, fraction = 2)
        BigDecimal grandTotal,

        OrderStatus status,

        LocalDateTime deliveryDate

) {}
