package com.globant.trainingnewgen.service.client.validation;

public interface Validator<T> {

    void validate(T object);

}
