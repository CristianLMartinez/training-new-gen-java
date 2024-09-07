package com.globant.trainingnewgen.service.impl;

import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ExceptionCode;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;

import java.util.Optional;

public abstract class BaseService<T, D> {

    protected abstract boolean hasChanges(T existingEntity, D dto);

    protected void validateAndThrowIfExists(Optional<T> entity, ExceptionCode exceptionCode, String errorMessage) {
        entity.ifPresent(e -> {
            throw new EntityConflictException(errorMessage, exceptionCode);
        });
    }

    protected T retrieveOrThrow(Optional<T> entity, ExceptionCode exceptionCode, String errorMessage) {
        return entity.orElseThrow(() -> new ResourceNotFoundException(errorMessage, exceptionCode));
    }

}
