package com.globant.trainingnewgen.model.dto;

import com.globant.trainingnewgen.model.entity.OrderItems;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemsDto(

        @NotNull(message = "Product uuid can't be null")
        UUID productUuid,

        String productName,

        @Min(1)
        @Max(99)
        int quantity

) {
        public OrderItemsDto(OrderItems orderItems){
                this(orderItems.getProduct().getUuid(), orderItems.getProduct().getFantasyName(), orderItems.getQuantity());
        }
}

