package com.example.factories;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientFactory {

    public WebClient createClient() {
       return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }
}
