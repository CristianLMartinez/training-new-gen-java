package com.globant.trainingnewgen.exception.custom;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String message, ExceptionCode exceptionCode){
        super(message, exceptionCode);
    }

}
