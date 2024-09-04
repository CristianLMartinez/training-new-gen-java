package com.globant.trainingnewgen.model.mapper;

import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.model.entity.Order;
import com.globant.trainingnewgen.model.entity.Product;


public class OrderMapper {

    private OrderMapper() {}

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

