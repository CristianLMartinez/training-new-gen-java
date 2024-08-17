package com.globant.trainingnewgen.map;

import com.globant.trainingnewgen.dto.ClientDto;
import com.globant.trainingnewgen.model.Client;

public class ClientMapper {

    private ClientMapper(){
        throw new IllegalArgumentException("Utility class");
    }

    public static ClientDto entityToDto(Client client){
        return ClientDto.builder()
                .document(client.getDocument())
                .name(client.getName())
                .email(client.getEmail())
                .deliveryAddress(client.getDeliveryAddress())
                .phone(client.getPhone())
                .build();
    }

    public static Client dtoToEntity(ClientDto dto){
        return Client.builder()
                .name(dto.name())
                .document(dto.document())
                .deliveryAddress(dto.deliveryAddress())
                .email(dto.email())
                .phone(dto.phone())
                .build();
    }


}
