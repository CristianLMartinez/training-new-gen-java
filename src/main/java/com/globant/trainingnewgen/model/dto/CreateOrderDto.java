package com.globant.trainingnewgen.model.dto;


import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.model.entity.Order;
import com.globant.trainingnewgen.model.entity.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
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

        public Order toEntity(){
                return Order.builder()
                        .client(Client.builder().document(clientDocument()).build())
                        .product(Product.builder().uuid(productUuid()).build())
                        .quantity(quantity())
                        .extraInformation(extraInformation())
                        .build();
        }

}
