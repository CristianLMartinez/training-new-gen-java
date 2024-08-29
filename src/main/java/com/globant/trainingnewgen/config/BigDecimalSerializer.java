package com.globant.trainingnewgen.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    private static final NumberFormat formatter = new DecimalFormat("0.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeNumber(formatter.format(value));
        }
    }
}
