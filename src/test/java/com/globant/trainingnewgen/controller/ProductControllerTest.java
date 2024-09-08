package com.globant.trainingnewgen.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.model.dto.ProductDto;
import com.globant.trainingnewgen.model.dto.SalesData;
import com.globant.trainingnewgen.model.dto.SalesReportDto;
import com.globant.trainingnewgen.model.entity.ProductCategory;
import com.globant.trainingnewgen.service.ProductService;
import com.globant.trainingnewgen.service.SalesReportService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.*;
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

    private ProductDto responseBody;

    @BeforeEach
    void setUp() {
        responseBody = ProductDto.builder()
                .uuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5249d"))
                .description("Burger with cheese")
                .available(Boolean.TRUE)
                .category(ProductCategory.HAMBURGERS_AND_HOT_DOGS)
                .fantasyName("Classic Burger")
                .price(BigDecimal.valueOf(1000.02))
                .build();
    }

    @Test
    @DisplayName("Test create a new product")
    void testCreateProduct() throws Exception {

        var requestBody = objectMapper.writeValueAsString(responseBody);

        given(productService.create(any(ProductDto.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.fantasyName").value(responseBody.fantasyName()),
                        jsonPath("$.category").value(responseBody.category().toString()),
                        jsonPath("$.uuid").value(responseBody.uuid().toString()),
                        jsonPath("$.description").value(responseBody.description()));


        verify(productService).create(any(ProductDto.class));

    }

    @Test
    @DisplayName("Test create a new product")
    void testCreateProductThrowResourceNotFoundException() throws Exception {
        var exception = new EntityConflictException("Product already exists", ExceptionCode.COMBO_ALREADY_EXISTS);

        var requestBody = objectMapper.writeValueAsString(responseBody);

        given(productService.create(any(ProductDto.class))).willThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpectAll(jsonPath("$.description").value(exception.getMessage()));

        verify(productService).create(any(ProductDto.class));

    }


    @Test
    @DisplayName("Test get product by uuid")
    void testGetProductByUuid() throws Exception {

        var uuid = responseBody.uuid().toString();

        given(productService.getProductByUuid(any(UUID.class))).willReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.uuid").value(responseBody.uuid().toString()),
                        jsonPath("$.fantasyName").value(responseBody.fantasyName()),
                        jsonPath("$.price").value(responseBody.price()));

        verify(productService).getProductByUuid(any(UUID.class));
    }

    @Test
    @DisplayName("Test update product by uuid")
    void testUpdateProduct() throws Exception {
        var requestBody = objectMapper.writeValueAsString(responseBody);
        var uuid = responseBody.uuid();

        mockMvc.perform(MockMvcRequestBuilders.put("/products/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());

        verify(productService).updateProduct(eq(uuid), any(ProductDto.class));
    }

    @Test
    @DisplayName("Test delete product by uuid")
    void testDeleteProduct() throws Exception {
        var uuid = responseBody.uuid();

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + uuid))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(eq(uuid));
    }

    @Test
    @DisplayName("Test search products by fantasy name")
    void testSearchProductsByFantasyName() throws Exception {
        var products = List.of(responseBody);
        var fantasyName = responseBody.fantasyName();

        given(productService.searchProductsByFantasyName(fantasyName)).willReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("q", fantasyName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].uuid").value(responseBody.uuid().toString()),
                        jsonPath("$[0].fantasyName").value(responseBody.fantasyName()),
                        jsonPath("$[0].description").value(responseBody.description())
                );

        given(productService.searchProductsByFantasyName(fantasyName)).willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("q", fantasyName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        var exception = new IllegalArgumentException("Parameter 'q' is required");

        given(productService.searchProductsByFantasyName(anyString())).willThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("q", fantasyName))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value(exception.getMessage()));
    }

    @Test
    @DisplayName("Test sales report")
    void testGetSalesReport() throws Exception {

        var startDate = "20240806";
        var endDate = "20240906";

        var salesData1 = SalesData.builder()
                .productName(responseBody.fantasyName())
                .unitsSold(2)
                .totalRevenue(BigDecimal.valueOf(11.98)).build();

        var salesData2 =   SalesData.builder()
                .productName("Buffalo Wings")
                .unitsSold(2)
                .totalRevenue(BigDecimal.valueOf(8.99))
                .build();

        var leastSoldProducts = List.of("Apple Pie", "Buffalo Wings", "Carrot Cake");

        var salesReport = SalesReportDto.builder()
                .salesData(List.of(salesData1, salesData2))
                .mostSoldProducts(List.of(responseBody.fantasyName()))
                .leastSoldProducts(leastSoldProducts)
                .build();

        given(salesReportService.getSalesReport(startDate, endDate)).willReturn(salesReport);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/products/sales_report/%s/%s", startDate, endDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.salesData[0].productName").value(responseBody.fantasyName()),
                        jsonPath("$.mostSoldProducts[0]").value(responseBody.fantasyName()),
                        jsonPath("$.leastSoldProducts[1]").value(leastSoldProducts.get(1)));

        verify(salesReportService).getSalesReport(eq(startDate), eq(endDate));


    }


}