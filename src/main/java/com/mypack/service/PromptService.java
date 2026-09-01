package com.mypack.service;

import com.mypack.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptService {
    public String buildPrompt(
            String userMessage,
            String intent,
            List<Message> history
    ) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are a helpful AI Assistant.
                Rules:
                - Answer clearly and accurately.
                - Use the conversation history when relevant.
                - If the user asks about programming, provide practical explanations.
                - Do not invent information.
                """);

        prompt.append("\nDetected intent: ")
                .append(intent)
                .append("\n\n");

        if (history != null && !history.isEmpty()) {
            prompt.append("Conversation history:\n");
            for (Message message : history) {
                // FIXED: Use lower-case 'message' (the loop variable), not Class 'Message'
                prompt.append(message.getRole().name())
                        .append(": ")
                        .append(message.getContent())
                        .append("\n");
            }
        }

        prompt.append("\nCurrent user message:\n");
        prompt.append(userMessage);

        return prompt.toString();
    }
}