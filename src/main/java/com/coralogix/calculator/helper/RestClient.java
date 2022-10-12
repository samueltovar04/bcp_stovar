package com.coralogix.calculator.helper;

import com.coralogix.calculator.exception.GenericException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T getForEntity(Class<T> clazz, String url ,Object... uriVariables) {

        HttpEntity<String> request = new HttpEntity<>("");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(response, javaType);
    }

    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (HttpStatus.OK.equals(response.getStatusCode()) ||
                HttpStatus.CREATED.equals(response.getStatusCode())) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                throw new GenericException("rest client exception "+ e.getMessage(), e.getCause());
            }
        } else {
            log.error("No data found by readValue {}", response.getStatusCode());
        }
        return result;
    }
}
