package com.globant.trainingnewgen.model.mapper;

import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.model.dto.OrderItemsDto;
import com.globant.trainingnewgen.model.entity.Order;

import java.util.List;


public class OrderMapper {

    private OrderMapper() {}

    public static OrderDto entityToOrderDto(Order order) {
        List<OrderItemsDto> orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemsDto::new)
                .toList();

        return OrderDto.builder()
                .clientDocument(order.getClient().getDocument())
                .creationDateTime(order.getCreationDateTime())
                .uuid(order.getUuid())
                .products(orderItems)
                .extraInformation(order.getExtraInformation())
                .subTotal(order.getSubTotal())
                .grandTotal(order.getGrandTotal())
                .tax(order.getTax())
                .delivered(order.isDelivered())
                .deliveryDate(order.getDeliveryDate())
                .build();
    }
}

