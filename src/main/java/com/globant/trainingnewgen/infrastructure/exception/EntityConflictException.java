package com.globant.trainingnewgen.infrastructure.exception;

public class EntityConflictException extends RuntimeException {

    public EntityConflictException(String message){
        super(message);
    }

}
