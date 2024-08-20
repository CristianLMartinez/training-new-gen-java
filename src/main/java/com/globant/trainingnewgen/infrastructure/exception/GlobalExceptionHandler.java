package com.globant.trainingnewgen.infrastructure.exception;


import com.globant.trainingnewgen.infrastructure.exception.enums.ExceptionCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiError> resourceNotFoundExceptionHandler(Exception ex) {
        logger.warn("Resource not found exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        ExceptionCode.USER_ALREADY_EXISTS,
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getClass().getName()));
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ApiError> handleEntityConflictException(EntityConflictException ex) {
        logger.error("Entity conflict exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        ExceptionCode.NO_CHANGES,
                        LocalDateTime.now(),
                        "Conflict error",
                        ex.getClass().getName()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ApiError> validationExceptionHandler(Exception ex) {
        logger.warn("Validation exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        ExceptionCode.USER_ALREADY_EXISTS,
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getClass().getName()));
    }


    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> exceptionHandler(Exception ex) {
        logger.error("Something went wrong {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        ExceptionCode.USER_ALREADY_EXISTS,
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getClass().getName()
                ));
    }


}
