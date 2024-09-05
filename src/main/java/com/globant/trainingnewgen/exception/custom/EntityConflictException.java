package com.globant.trainingnewgen.exception.custom;

public class EntityConflictException extends CustomException {
    public EntityConflictException(String message, ExceptionCode exceptionCode){
        super(message, exceptionCode);
    }

}
