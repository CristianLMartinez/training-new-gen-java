package com.globant.trainingnewgen.exception.custom;

import com.globant.trainingnewgen.exception.ExceptionCode;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String message, ExceptionCode exceptionCode){
        super(message, exceptionCode);
    }

}
