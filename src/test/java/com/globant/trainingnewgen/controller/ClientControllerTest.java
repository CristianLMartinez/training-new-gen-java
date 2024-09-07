package com.globant.trainingnewgen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.service.ClientService;
import com.globant.trainingnewgen.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("Test create client is successful")
    void createClient() throws Exception {
        var responseBody = ClientDto.builder()
                .document("CC-123456")
                .name("John Doe")
                .email("john@mail.com")
                .phone("321-1234321")
                .deliveryAddress("Cra 25 #23-23")
                .build();

        String requestBody = objectMapper.writeValueAsString(responseBody);

        given(clientService.create(any(ClientDto.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(responseBody.name()))
                .andExpect(jsonPath("$.document").value(responseBody.document()))
                .andExpect(jsonPath("$.deliveryAddress").value(responseBody.deliveryAddress()))
                .andExpect(jsonPath("$.phone").value(responseBody.phone()))
                .andExpect(jsonPath("$.email").value(responseBody.email()));

        verify(clientService).create(any(ClientDto.class));
    }

    @Test
    @DisplayName("Test get client by document is successful")
    void getClient() throws Exception {
        var responseBody = ClientDto.builder()
                .document("CC-123456")
                .name("John Doe")
                .email("john@mail.com")
                .phone("321-1234321")
                .deliveryAddress("Cra 25 #23-23")
                .build();

        given(clientService.getClientByDocument(any(String.class), any(Boolean.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/CC-123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseBody.name()))
                .andExpect(jsonPath("$.document").value(responseBody.document()))
                .andExpect(jsonPath("$.deliveryAddress").value(responseBody.deliveryAddress()))
                .andExpect(jsonPath("$.phone").value(responseBody.phone()))
                .andExpect(jsonPath("$.email").value(responseBody.email()));

        verify(clientService).getClientByDocument(any(String.class), any(Boolean.class));
    }

    @Test
    @DisplayName("Test get clients is successful")
    void getClients() throws Exception {
        var responseBody = List.of(
                ClientDto.builder()
                        .document("CC-123456")
                        .name("John Doe")
                        .email("john@mail.com")
                        .phone("321-1234321")
                        .deliveryAddress("Cra 25 #23-23")
                        .build()
        );

        given(clientService.getClients(any(String.class), any(String.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(responseBody.get(0).name()))
                .andExpect(jsonPath("$[0].document").value(responseBody.get(0).document()))
                .andExpect(jsonPath("$[0].deliveryAddress").value(responseBody.get(0).deliveryAddress()))
                .andExpect(jsonPath("$[0].phone").value(responseBody.get(0).phone()))
                .andExpect(jsonPath("$[0].email").value(responseBody.get(0).email()));

        verify(clientService).getClients(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("Test get clients fails with ResourceNotFoundException")
    void getClientsFailsWithResourceNotFoundException() throws Exception {
        var exception = new ResourceNotFoundException("Client not found", ExceptionCode.CLIENT_NOT_FOUND);

        given(clientService.getClients(any(String.class), any(String.class))).willThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ExceptionCode.CLIENT_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.description").value("Client not found"))
                .andExpect(jsonPath("$.exception").value(ExceptionCode.CLIENT_NOT_FOUND.getDescription()));

        verify(clientService).getClients(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("Test get orders by client document is successful")
    void getOrdersByClientDocument() throws Exception {
        var responseBody = List.of(
                OrderDto.builder()
                        .uuid(UUID.randomUUID())
                        .quantity(1)
                        .extraInformation("Extra info")
                        .subTotal(BigDecimal.valueOf(100))
                        .tax(BigDecimal.valueOf(10))
                        .grandTotal(BigDecimal.valueOf(110))
                        .build()
        );

        given(orderService.getOrdersByClientDocument(any(String.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/CC-123456/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(responseBody.get(0).quantity()))
                .andExpect(jsonPath("$[0].extraInformation").value(responseBody.get(0).extraInformation()))
                .andExpect(jsonPath("$[0].subTotal").value(responseBody.get(0).subTotal()))
                .andExpect(jsonPath("$[0].tax").value(responseBody.get(0).tax()))
                .andExpect(jsonPath("$[0].grandTotal").value(responseBody.get(0).grandTotal()));

        verify(orderService).getOrdersByClientDocument(any(String.class));
    }

    @Test
    @DisplayName("Test update client is successful")
    void updateClient() throws Exception {
        var requestBody = ClientDto.builder()
                .document("CC-123456")
                .name("John Doe")
                .email("john@mail.com")
                .phone("321-1234321")
                .deliveryAddress("Cra 25 #23-23")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/clients/CC-123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNoContent());

        verify(clientService).updateClient(any(String.class), any(ClientDto.class));
    }

    @Test
    @DisplayName("Test delete client is successful")
    void deleteClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/CC-123456"))
                .andExpect(status().isNoContent());

        verify(clientService).deleteClient(any(String.class));
    }
}