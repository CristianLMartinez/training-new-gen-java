package com.globant.trainingnewgen.service.client.validation;

import com.globant.trainingnewgen.exception.EntityConflictException;
import com.globant.trainingnewgen.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAlreadyExistsValidation implements Validator<String> {

    private final ClientRepository clientRepository;

    @Override
    public void validate(String document) {
            clientRepository.findClientByDocument(document)
                    .orElseThrow(() -> new EntityConflictException(String.format("User with document %s already exists", document)));
    }


}
