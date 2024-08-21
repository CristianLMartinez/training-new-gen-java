package com.globant.trainingnewgen.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final Map<Class<? extends Exception>, ExceptionCode> exceptionCodeMap;

    public GlobalExceptionHandler() {
        exceptionCodeMap = new HashMap<>();
        exceptionCodeMap.put(ResourceNotFoundException.class, ExceptionCode.USER_ALREADY_EXISTS);
        exceptionCodeMap.put(EntityConflictException.class, ExceptionCode.NO_CHANGES);
        exceptionCodeMap.put(ValidationException.class, ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION);
        exceptionCodeMap.put(MethodArgumentNotValidException.class, ExceptionCode.INVALID_DOCUMENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ApiError> handleEntityConflictException(EntityConflictException ex) {
        return buildResponseEntity(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
        return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String description = fieldError != null ? fieldError.getDefaultMessage() : "Invalid input";
        logger.warn("Validation exception: {}", description);

        return buildResponseEntity(HttpStatus.BAD_REQUEST, description, ExceptionCode.INVALID_DOCUMENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ApiError> buildResponseEntity(Exception ex, HttpStatus status) {
        ExceptionCode exceptionCode = exceptionCodeMap.getOrDefault(ex.getClass(), ExceptionCode.SERVER_ERROR);
        logger.error("{}: {}", exceptionCode.getDescription(), ex.getMessage());

        ApiError apiError = new ApiError(
                exceptionCode.getCode(),
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getClass().getName());

        return ResponseEntity.status(status).body(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, String description, ExceptionCode exceptionCode) {
        logger.warn("{}: {}", exceptionCode.getDescription(), description);

        ApiError apiError = new ApiError(
                exceptionCode.getCode(),
                LocalDateTime.now(),
                description,
                exceptionCode.getDescription());

        return ResponseEntity.status(status).body(apiError);
    }

}
