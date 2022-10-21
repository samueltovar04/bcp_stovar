package com.coralogix.calculator.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecipeWebService {
    private final WebClient webClient;
    private final String host;

    @Autowired
    public RecipeWebService(@Value("${client.host}") String host,
            WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl(host)
                .build();
        this.host = host;
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
