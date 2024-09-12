package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.model.dto.CreateOrderDto;
import com.globant.trainingnewgen.model.dto.OrderDto;
import com.globant.trainingnewgen.model.dto.OrderItemsDto;
import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.model.entity.Order;
import com.globant.trainingnewgen.model.entity.OrderItems;
import com.globant.trainingnewgen.model.entity.Product;
import com.globant.trainingnewgen.repository.ClientRepository;
import com.globant.trainingnewgen.repository.OrderRepository;
import com.globant.trainingnewgen.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    public void testCreateOrders() {

        UUID productUuid = UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5242c");
        String clientDocument = "1234567890";
        int quantity = 5;
        BigDecimal pricePerUnit = new BigDecimal("100.00");

        Product product = new Product();
        product.setId(1L);
        product.setUuid(productUuid);
        product.setFantasyName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(pricePerUnit);


        Client client = Client.builder()
                .id(1L)
                .document("CC-123456")
                .name("John Doe")
                .email("john@mail.com")
                .phone("321-1234321")
                .deliveryAddress("Cra 25 #23-23")
                .build();


        List<OrderItemsDto> orderItemsDtoList = List.of(OrderItemsDto.builder()
                .productUuid(productUuid)
                .build());

        List<OrderItems> orderItemsList = List.of(OrderItems.builder()
                .product(product)
                .quantity(2).build());


        Order order = Order.builder()
                .uuid(UUID.fromString("be1cf063-79a8-4f25-9f04-18a157f5242d"))
                .orderItems(orderItemsList)
                .client(client)
                .creationDateTime(LocalDateTime.now())
                .build();

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .clientDocument(client.getDocument())
                .products(orderItemsDtoList)
                .extraInformation("informacion extra").build();


        when(clientRepository.findClientByDocument(Mockito.anyString(), Mockito.eq(false))).thenReturn(Optional.of(client));

        when(productRepository.findProductByUuid(any(UUID.class))).thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L);
            return savedOrder;
        });

        OrderDto orderDto = orderService.create(createOrderDto);

        verify(clientRepository).findClientByDocument(Mockito.anyString(), Mockito.eq(false));
        verify(productRepository).findProductByUuid(any(UUID.class));
        verify(orderRepository).save(any(Order.class));
        assertNotNull(orderDto);




    }

}
