package com.globant.trainingnewgen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class JacksonConfiguration {

    private final TimeZoneProperties timeZoneProperties;

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SimpleModule bigDecimalModule = new SimpleModule();
        bigDecimalModule.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        objectMapper.registerModule(bigDecimalModule);

        objectMapper.setTimeZone(TimeZone.getTimeZone(timeZoneProperties.getTimeZone()));
        return objectMapper;
    }

}
