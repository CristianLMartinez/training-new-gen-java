package com.globant.trainingnewgen.exception;


import com.globant.trainingnewgen.exception.custom.*;
import com.globant.trainingnewgen.model.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(CustomException ex) {
        logger.warn("Resource not found exception: {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(ex.getExceptionCode().getCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(ex.getExceptionCode().getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorResponse> handleEntityConflictException(EntityConflictException ex) {
        logger.error("Entity conflict exception: {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(ex.getExceptionCode().getCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(ex.getExceptionCode().getDescription())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorResponse> validationExceptionHandler(Exception ex) {
        logger.warn("Validation exception: {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> methodArgumentValidExceptionHandler(MethodArgumentNotValidException ex) {
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

        var errorResponse = ErrorResponse.builder()
                .code(ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getCode())
                .timestamp(LocalDateTime.now())
                .description(description)
                .exception(ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }




    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<ErrorResponse> handlerMethodValidationException(HandlerMethodValidationException ex) {
        logger.warn("{}", ex.getClass());
        String description = Arrays.toString(ex.getDetailMessageArguments());

        var errorResponse = ErrorResponse.builder()
                .code(ExceptionCode.INCOMPLETE_OR_INCORRECT_INFORMATION.getCode())
                .timestamp(LocalDateTime.now())
                .description(description)
                .exception(ex.getClass().getName())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal argument exception: {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        logger.error("Something went wrong {}", ex.getMessage());
        var errorResponse = ErrorResponse.builder()
                .code(ExceptionCode.SERVER_ERROR.getCode())
                .timestamp(LocalDateTime.now())
                .description(ex.getMessage())
                .exception(ExceptionCode.SERVER_ERROR.getDescription())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


}
