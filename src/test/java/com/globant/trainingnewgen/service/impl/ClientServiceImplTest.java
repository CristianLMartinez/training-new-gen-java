package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.model.dto.ClientDto;
import com.globant.trainingnewgen.model.entity.Client;
import com.globant.trainingnewgen.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Test client service implementation")
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testCreateClient() {
        // GIVEN
        var clientDto = ClientDto.builder()
                .document("CC-1234569")
                .build();

        Client client = Client.builder()
                .document(clientDto.document())
                .isDeleted(false)
                .build();

        // WHEN
        when(clientRepository.findClientByDocument(anyString())).thenReturn(Optional.empty());
        when(clientRepository.save(client)).thenReturn(client);

        ClientDto clientReturned = clientService.create(clientDto);

        // THEN
        verify(clientRepository).save(client);
        assertEquals(clientDto.document(), clientReturned.document());

    }

    @Test
    @DisplayName("Test create client when a client already exists should restore it")
    void createWhenClientExistsAndIsDeletedShouldRestoreClient() {
        var clientDto = ClientDto.builder()
                .document("CC-1234567890")
                .build();

        Client existingClient = new Client();
        existingClient.setDeleted(true);

        when(clientRepository.findClientByDocument(anyString())).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(existingClient);


        ClientDto restoredClient = clientService.create(clientDto);

        verify(clientRepository, times(1)).findClientByDocument(eq("CC-1234567890"));
        assertNotNull(restoredClient);
        assertFalse(existingClient.isDeleted(), "Field *isDeleted* should be false");
    }

    @Test
    @DisplayName("Test search client when this exist ")
    void testSearchClient() {

        String document = "CC-1234569";
        Boolean isDeleted = false;

        Client mockClient = new Client();

        when(clientRepository.findClientByDocument(anyString(), anyBoolean())).thenReturn(Optional.of(mockClient));

        ClientDto clientDto = clientService.getClientByDocument(document, isDeleted);

        assertNotNull(clientDto);
        verify(clientRepository, times(1)).findClientByDocument(document, isDeleted);
    }

    @Test
    @DisplayName("Test search client when it exists")
    void testSearchClientShouldThrowException() {
        String document = "CC-1234569";
        Boolean isDeleted = false;

        var exception = new ResourceNotFoundException(
                String.format("Can't find user with document %s", document),
                ExceptionCode.CLIENT_NOT_FOUND);

        when(clientRepository.findClientByDocument(anyString(), anyBoolean())).thenThrow(exception);

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientByDocument(document, isDeleted));
    }

    @Test
    void testUpdateClientSuccess() {
        String document = "CC-123456";
        ClientDto requestBody = ClientDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .deliveryAddress("123 Main St")
                .build();

        Client existingClient = Client.builder()
                .name("Old Name")
                .email("old.email@example.com")
                .phone("987654321")
                .deliveryAddress("Old Address")
                .isDeleted(false)
                .build();

        // Simulamos la recuperación del cliente
        when(clientRepository.findClientByDocument(anyString(), false)).thenReturn(Optional.of(existingClient));
        when(clientService.validateAndRetrieveClientByDocument(document, false)).thenReturn(existingClient);

        // Simulamos que se detectan cambios (hasChanges() devuelve true)
        when(clientService.hasChanges(existingClient, requestBody)).thenReturn(true);

        // Act: actualizamos el cliente
        clientService.updateClient(document, requestBody);

        // Assert
        // Verificamos que los datos fueron actualizados correctamente
        assertEquals(requestBody.name(), existingClient.getName());
        assertEquals(requestBody.email(), existingClient.getEmail());
        assertEquals(requestBody.phone(), existingClient.getPhone());
        assertEquals(requestBody.deliveryAddress(), existingClient.getDeliveryAddress());

        // Verificamos que se llamó al método save del repositorio
        verify(clientRepository, times(1)).save(existingClient);
    }



}