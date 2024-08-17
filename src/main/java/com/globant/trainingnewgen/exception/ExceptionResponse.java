package com.globant.trainingnewgen.exception;

import java.sql.Timestamp;

public record ExceptionResponse(
        String code,
        Timestamp timestamp,
        String description,
        String exception
) {
}
