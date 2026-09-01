package com.mypack.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class LlmClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private final String apiUrl;
    private final String model;

    public LlmClient(RestClient restClient, ObjectMapper objectMapper,
                     @Value("${llm.api.url:http://localhost:11434/api/generate}") String apiUrl,
                     @Value("${llm.model:llama3}") String model) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.apiUrl = apiUrl;
        this.model = model;
    }

    public String generateResponse(String prompt) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("model", model);
            request.put("prompt", prompt);
            request.put("stream", false); // Full response ek baar me lene ke liye

            String response = restClient
                    .post()
                    .uri(apiUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path("response");

            if (content.isMissingNode() || content.isNull()) {
                throw new RuntimeException("Invalid response received from Ollama API: " + response);
            }
            return content.asText();

        } catch (Exception e) {
            throw new RuntimeException("Ollama LLM request failed: " + e.getMessage(), e);
        }
    }
}