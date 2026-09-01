package com.mypack.service;

import com.mypack.dto.Response.ChatResponse;
import org.springframework.stereotype.Service;

@Service


public class ResponseService {
    public ChatResponse chatResponse(
            Long conversationId,
            String Intent,
            String message
    ){
        return new ChatResponse(
                conversationId,
                Intent,
                message        );
    }
    public ChatResponse createChatResponse(Long conversationId, String intent, String aiResponse) {
        ChatResponse response = new ChatResponse();

        // Apne ChatResponse DTO ke getters/setters ke hisab se set karein:
        response.setConversationId(String.valueOf(conversationId));
        response.setIntent(intent);
        response.setResponse(aiResponse); // ya response.setMessage(aiResponse)

        return response;
    }
}
