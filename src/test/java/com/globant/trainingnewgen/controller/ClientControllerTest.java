package com.globant.trainingnewgen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;


    @Test
    @DisplayName("Test create user is success")
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
                        .content( requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(responseBody.name()))
                .andExpect(jsonPath("$.document").value(responseBody.document()))
                .andExpect(jsonPath("$.deliveryAddress").value(responseBody.deliveryAddress()))
                .andExpect(jsonPath("$.phone").value(responseBody.phone()))
                .andExpect(jsonPath("$.email").value(responseBody.email()));

        verify(clientService).create(any(ClientDto.class));

    }

}