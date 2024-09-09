package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.entity.Client;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        client = Client.builder()
                .document("CC-123456")
                .name("John Doe")
                .email("john.doe@gmail.com")
                .phone("123-1233454")
                .deliveryAddress("Tokyo AV. 20")
                .build();
    }


    @Test
    @Transactional
    @DisplayName("Should find client by document")
    void testFindClientByDocument() {
        clientRepository.save(client);
        Optional<Client> optionalClient = clientRepository.findClientByDocument("CC-123456", null);
        assertTrue(optionalClient.isPresent(), "El cliente debería estar presente");
        var foundClient = optionalClient.get();
        assertEquals(client.getDocument(), foundClient.getDocument());
    }

    @Test
    @DisplayName("Should throw exception when try to find a client")
    void shouldReturnEmptyOptionalWhenClientNotFound() {
        Optional<Client> optionalClient = clientRepository.findClientByDocument("CC-999999", null);
        assertTrue(optionalClient.isEmpty(), "El cliente no debería estar presente");
    }

    @Test
    @DisplayName("Should delete a client")
    void shouldDeleteClient() {
        clientRepository.save(client);
        clientRepository.delete(client);
        Optional<Client> optionalClient = clientRepository.findClientByDocument(client.getDocument());
        System.out.println(clientRepository.findAll().size());
        assertTrue(optionalClient.isEmpty(), "No deberia haber un cliente");
    }


}