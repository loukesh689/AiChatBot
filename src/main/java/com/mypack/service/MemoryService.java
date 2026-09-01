package com.mypack.service;

import com.mypack.entity.Message;
import com.mypack.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MemoryService {
    private static final int MAX_MEMORY_MESSAGES = 20;

    @Autowired
    MessageRepository messageRepository;

    // Complete history UI ke liye
    public List<Message> getConversationHistory(Long conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

    // Complete History text for AI model ke liye
    public String buildHistoryText(Long conversationId) {
        List<Message> messages = getConversationHistory(conversationId);
        StringBuilder history = new StringBuilder();
        for (Message message : messages) {
            history.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }
        return history.toString();
    }

    // Recent 20 messages fetch karne ke liye
    public List<Message> getRecentMessage(Long conversationId) {
        List<Message> messages = messageRepository.findTop20ByConversationIdOrderByCreatedAtDesc(conversationId);
        // Database se desc mein aata hai, prompt ke liye oldest -> newest chahiye
        Collections.reverse(messages);
        return messages;
    }

    // ✅ FIX 1: Method ka naam badal kar 'buildRecentHistoryText' kar diya
    // Recent history ko readable text mein convert karna
    public String buildRecentHistoryText(Long conversationId) {

        // ✅ FIX 2: Local variable se 'public' keyword hata diya aur correct method call kiya
        List<Message> messages = getRecentMessage(conversationId);

        StringBuilder history = new StringBuilder();

        for (Message message : messages) {
            history.append(message.getRole().name());
            history.append(": ");
            history.append(message.getContent());
            history.append("\n");
        }

        return history.toString();
    }
}