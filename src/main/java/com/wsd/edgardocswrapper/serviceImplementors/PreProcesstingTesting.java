package com.wsd.edgardocswrapper.serviceImplementors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class PreProcesstingTesting {
    private final RestTemplate restTemplate;

    public PreProcesstingTesting(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<String> asyncAPICall(String token) {
        try {
            // Set up the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the asynchronous API call to ASP.NET Core
            String response = restTemplate.exchange(
                    "http://localhost:5000/api/v1/docx/test", // ASP.NET Core API URL
                    HttpMethod.GET,
                    entity,
                    String.class
            ).getBody();

            // Return the response
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture("Failed to retrieve data.");
        }
    }
    
}
