package com.mypack.service;

import org.springframework.stereotype.Service;

@Service
public class IntentService {

    // Method name must start with lowercase 'i'
    public String identifyIntent(String message) {
        if (message == null || message.isBlank()) {
            return "GENERAL";
        }

        String text = message.toLowerCase();

        if (containsAny(text, "java", "code", "programming", "python", "javascript", "error", "bug", "compile")) {
            return "CODE";
        }

        if (containsAny(text, "weather", "temperature", "forecast", "rain", "sunny", "cloudy")) {
            return "WEATHER";
        }

        return "GENERAL";
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}