package com.stackroute.externalapicalling;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestClient;


import java.util.Map;

@SpringBootApplication
@RestController
public class ExternalapicallingApplication implements ApplicationRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");
    private final RestClient restClient = RestClient.create();

    public static void main(String[] args) {
        SpringApplication.run(ExternalapicallingApplication.class, args);
    }

    // Executes on startup
    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
        System.out.println("=== ApplicationRunner: Invoking JSONPlaceholder API ===");

        // Using RestTemplate
        String restTemplateResponse = restTemplate
                .getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
        System.out.println("RestTemplate: " + restTemplateResponse);

        // Using WebClient
        String webClientResponse = webClient.get()
                .uri("/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("WebClient: " + webClientResponse);

        // Using RestClient (Spring 6 / Boot 3+)
        String restClientResponse = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .body(String.class);
        System.out.println("RestClient: " + restClientResponse);
    }

    // Simple REST endpoint to trigger same calls manually
    @GetMapping("/invoke")
    public Map<String, Object> invokeApis() {
        String rtData = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", String.class);
        String wcData = webClient.get().uri("/posts/1").retrieve().bodyToMono(String.class).block();
        String rcData = restClient.get().uri("https://jsonplaceholder.typicode.com/posts/1").retrieve().body(String.class);

        return Map.of(
                "RestTemplate", rtData,
                "WebClient", wcData,
                "RestClient", rcData
        );
    }
}


// ApplicationRunner runs immediately after the app starts.

// @RestController lets you expose endpoints in the same class (/invoke endpoint here).

// RestTemplate → simple & synchronous (old but still supported).

// WebClient → reactive & async (recommended for non-blocking calls).

// RestClient → new in Spring 6+, combines simplicity of RestTemplate with fluent API.