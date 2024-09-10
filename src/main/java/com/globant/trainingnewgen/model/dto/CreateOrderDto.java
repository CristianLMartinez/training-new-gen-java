package com.globant.trainingnewgen.model.dto;


import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.model.entity.Order;
import com.globant.trainingnewgen.model.entity.OrderItems;
import com.globant.trainingnewgen.model.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CreateOrderDto(

        @NotNull(message = "Client document can't be null")
        @Pattern(regexp = "^(CE|CC|TI|P)-\\d{1,17}$", message = "Incorrect client document format")
        String clientDocument,

        @NotNull(message = "Products can't be null")
        List<OrderItemsDto> products,

        String extraInformation

) {

        public Order toEntity() {
                var order = Order.builder()
                        .client(Client.builder().document(clientDocument()).build())
                        .extraInformation(extraInformation())
                        .build();

                var orderProducts = products.stream()
                        .map(productDto -> OrderItems.builder()
                                .order(order) // Setting the order reference
                                .product(Product.builder().uuid(productDto.productUuid()).build())
                                .quantity(productDto.quantity())
                                .build())
                        .collect(Collectors.toList());

                order.setOrderItems(orderProducts);
                return order;
        }
}
