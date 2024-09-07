package com.globant.trainingnewgen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.service.ProductService;
import com.globant.trainingnewgen.service.SalesReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Test")
class ProductControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private SalesReportService salesReportService;

    @Test
    @DisplayName("Test create a new product")
    void testCreateProduct() throws Exception {
        var responseBody = ProductDto.builder()
                .uuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5249d"))
                .description("Burger with cheese")
                .available(Boolean.TRUE)
                .category(ProductCategory.HAMBURGERS_AND_HOT_DOGS)
                .fantasyName("Classic Burger")
                .price(BigDecimal.valueOf(1000.02))
                .build();

        var requestBody = objectMapper.writeValueAsString(responseBody);

        given(productService.create(any(ProductDto.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fantasyName").value(responseBody.fantasyName()))
                .andExpect(jsonPath("$.category").value(responseBody.category().toString()))
                .andExpect(jsonPath("$.uuid").value(responseBody.uuid().toString()))
                .andExpect(jsonPath("$.description").value(responseBody.description()));

        verify(productService).create(any(ProductDto.class));

    }



}