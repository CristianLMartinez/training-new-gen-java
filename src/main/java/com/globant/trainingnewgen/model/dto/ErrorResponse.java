package com.globant.trainingnewgen.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse", description = "schema to hold error response information")
@Builder
public record ErrorResponse(
        String code,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        LocalDateTime timestamp,
        String description,
        String exception
) {}