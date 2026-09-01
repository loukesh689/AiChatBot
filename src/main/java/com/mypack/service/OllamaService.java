package com.mypack.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.List;

@Service
public class OllamaService {

    private final RestClient restClient;

    public OllamaService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public String askOllama(String prompt) {
        // Ollama local API request payload format
        Map<String, Object> requestBody = Map.of(
                "model", "llama3", // Jo model aapne pull kiya hai
                "prompt", prompt,
                "stream", false
        );

        Map response = restClient.post()
                .uri("/api/generate")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        return response != null ? (String) response.get("response") : "No response from Ollama";
    }
}