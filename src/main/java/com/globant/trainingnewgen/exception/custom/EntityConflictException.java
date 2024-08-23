package com.globant.trainingnewgen.exception.custom;

import com.globant.trainingnewgen.exception.ExceptionCode;

public class EntityConflictException extends CustomException {
    public EntityConflictException(String message, ExceptionCode exceptionCode){
        super(message, exceptionCode);
    }

}
