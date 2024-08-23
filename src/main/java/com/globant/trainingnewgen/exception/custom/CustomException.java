package com.globant.trainingnewgen.exception.custom;

import com.globant.trainingnewgen.exception.ExceptionCode;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    private ExceptionCode exceptionCode;

    CustomException(String message, ExceptionCode exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode;
    }

}
