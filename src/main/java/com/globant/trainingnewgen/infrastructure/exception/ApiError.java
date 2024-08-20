package com.globant.trainingnewgen.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.globant.trainingnewgen.infrastructure.exception.enums.ExceptionCode;

import java.time.LocalDateTime;

public record ApiError(
        ExceptionCode code,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime timestamp,
        String description,
        String exception
) {
}
