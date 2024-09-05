package com.globant.trainingnewgen.runner;

import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
@RequiredArgsConstructor
public class ClientSeedRunner implements CommandLineRunner {

    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        Client client1 = Client.builder()
                .document("CC-123456789")
                .name("Juan Perez")
                .email("juan.perez@mail.com")
                .phone("310-1234567")
                .deliveryAddress("Calle 123, Bogotá")
                .build();

        Client client2 = Client.builder()
                .document("CC-987654321")
                .name("Maria Gomez")
                .email("maria.gomez@mail.com")
                .phone("320-7654321")
                .deliveryAddress("Carrera 456, Medellín")
                .build();

        Client client3 = Client.builder()
                .document("CE-123456")
                .name("Carlos Ruiz")
                .email("carlos.ruiz@mail.com")
                .phone("311-6543210")
                .deliveryAddress("Avenida 789, Cali")
                .build();

        Client client4 = Client.builder()
                .document("TI-654321")
                .name("Ana Torres")
                .email("ana.torres@mail.com")
                .phone("313-9876543")
                .deliveryAddress("Calle 321, Barranquilla")
                .build();

        Client client5 = Client.builder()
                .document("CC-456789123")
                .name("Luis Martinez")
                .email("luis.martinez@mail.com")
                .phone("312-4567891")
                .deliveryAddress("Carrera 987, Cartagena")
                .build();

        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);
        clientRepository.save(client4);
        clientRepository.save(client5);
    }




}