package com.globant.trainingnewgen.exception.custom;

import com.globant.trainingnewgen.exception.ExceptionCode;

public class ValidationException extends CustomException {

    public ValidationException(String message, ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }

}
