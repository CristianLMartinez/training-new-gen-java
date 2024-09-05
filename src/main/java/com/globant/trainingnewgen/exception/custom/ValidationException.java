package com.globant.trainingnewgen.exception.custom;

public class ValidationException extends CustomException {

    public ValidationException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

}
