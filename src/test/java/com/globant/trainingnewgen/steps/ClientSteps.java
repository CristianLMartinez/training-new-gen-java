package com.globant.trainingnewgen.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@RequiredArgsConstructor
@Slf4j
public class ClientSteps {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper;



}
