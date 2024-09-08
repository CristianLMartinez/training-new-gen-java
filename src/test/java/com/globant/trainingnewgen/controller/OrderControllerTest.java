package com.globant.trainingnewgen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
@DisplayName("Test for order controller")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;


    @Test
    @DisplayName("Test create new order")
    void testCreateOrder() throws Exception {
        var responseBody = OrderDto.builder()
                .uuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5242c"))
                .creationDateTime(LocalDateTime.now())
                .clientDocument("CC-123456789")
                .productUuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5249d"))
                .quantity(2)
                .extraInformation("Burger without onions")
                .build();

        var createOrderDto = CreateOrderDto.builder()
                .clientDocument(responseBody.clientDocument())
                .quantity(responseBody.quantity())
                .extraInformation(responseBody.extraInformation())
                .productUuid(responseBody.productUuid())
                .build();

        var requestBody = objectMapper.writeValueAsString(createOrderDto);
        given(orderService.create(any(CreateOrderDto.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.uuid").value(responseBody.uuid().toString()),
                        jsonPath("$.clientDocument").value(responseBody.clientDocument()),
                        jsonPath("$.delivered").value(false),
                        jsonPath("$.creationDateTime").value(responseBody.creationDateTime().toString()),
                        jsonPath("$.deliveryDate").doesNotExist()
                );

        verify(orderService).create(any(CreateOrderDto.class));

    }

    @Test
    @DisplayName("Test update delivery order")
    void testUpdateDeliveryOrder() throws Exception {
        var deliveryDate = LocalDateTime.of(LocalDate.of(2024, 9, 2), LocalTime.of(19, 15, 0));
        var formattedDeliveryDate = deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        var responseBody = OrderDto.builder()
                .uuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5242c"))
                .creationDateTime(LocalDateTime.now())
                .delivered(true)
                .deliveryDate(deliveryDate)
                .build();

        var uri = String.format("/orders/%s/delivered/%s", responseBody.uuid(), deliveryDate);

        given(orderService.updateDeliveredState(responseBody.uuid(), deliveryDate)).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.deliveryDate").value(formattedDeliveryDate),
                        jsonPath("$.delivered").value(true),
                        jsonPath("$.uuid").value(responseBody.uuid().toString()));

        verify(orderService).updateDeliveredState(responseBody.uuid(), deliveryDate);
    }



}