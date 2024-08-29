package com.globant.trainingnewgen.mapper;

import com.globant.trainingnewgen.dto.CreateOrderDto;
import com.globant.trainingnewgen.dto.OrderDto;
import com.globant.trainingnewgen.model.Client;
import com.globant.trainingnewgen.model.Order;
import com.globant.trainingnewgen.model.Product;


public class OrderMapper {

    private OrderMapper() {}

    public static Order createOrderDtotoEntity(CreateOrderDto orderDto){
        return Order.builder()
                .client(Client.builder().document(orderDto.clientDocument()).build())
                .product(Product.builder().uuid(orderDto.productUuid()).build())
                .quantity(orderDto.quantity())
                .extraInformation(orderDto.extraInformation())
                .build();
    }

    public static OrderDto entityToOrderDto(Order order){
        return OrderDto.builder()
                .clientDocument(order.getClient().getDocument())
                .creationDateTime(order.getCreationDateTime())
                .uuid(order.getUuid())
                .productUuid(order.getProduct().getUuid())
                .quantity(order.getQuantity())
                .extraInformation(order.getExtraInformation())
                .subTotal(order.getSubTotal())
                .grandTotal(order.getGrandTotal())
                .tax(order.getTax())
                .delivered(order.isDelivered())
                .deliveryDate(order.getDeliveryDate())
                .build();
    }

}

