package com.globant.trainingnewgen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.math.BigDecimal;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class JacksonConfiguration {

    private final TimeZoneProperties timeZoneProperties;

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        configurations(objectMapper);
        return objectMapper;
    }


    @Bean
    public MappingJackson2XmlHttpMessageConverter xmlConverter() {
        XmlMapper xmlMapper = new XmlMapper();
        configurations(xmlMapper);
        return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
    }

    private void configurations(ObjectMapper xmlMapper) {
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SimpleModule bigDecimalModule = new SimpleModule();
        bigDecimalModule.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        xmlMapper.registerModule(bigDecimalModule);

        xmlMapper.setTimeZone(TimeZone.getTimeZone(timeZoneProperties.getTimeZone()));
    }


}
