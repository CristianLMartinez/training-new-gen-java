package com.globant.trainingnewgen.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public record ApiError(
        String code,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime timestamp,
        String description,
        String exception
) {
}