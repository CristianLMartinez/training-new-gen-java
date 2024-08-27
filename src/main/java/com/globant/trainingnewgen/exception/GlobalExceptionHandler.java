package com.globant.trainingnewgen.exception;


import com.globant.trainingnewgen.exception.custom.CustomException;
import com.globant.trainingnewgen.exception.custom.EntityConflictException;
import com.globant.trainingnewgen.exception.custom.ResourceNotFoundException;
import com.globant.trainingnewgen.exception.custom.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiError> resourceNotFoundExceptionHandler(CustomException ex) {
        logger.warn("Resource not found exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        ExceptionCode.USER_ALREADY_EXISTS.getCode(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getExceptionCode().getDescription()));
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ApiError> handleEntityConflictException(EntityConflictException ex) {
        logger.error("Entity conflict exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        ExceptionCode.NO_CHANGES.getCode(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ExceptionCode.NO_CHANGES.getDescription()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ApiError> validationExceptionHandler(Exception ex) {
        logger.warn("Validation exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getCode(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ExceptionCode.COMBO_ALREADY_EXISTS.getDescription()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> methodArgumentValidExceptionHandler(MethodArgumentNotValidException ex) {
        var errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Field '%s': %s (rejected value: %s)",
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()))
                .toList();

        var description = String.join(", ", errorMessages);
        logger.warn(description);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getCode(),
                        LocalDateTime.now(),
                        description,
                        ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getDescription()));
    }




    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> exceptionHandler(Exception ex) {
        logger.error("Something went wrong {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        ExceptionCode.USER_ALREADY_EXISTS.getCode(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getClass().getName()
                ));
    }


}
