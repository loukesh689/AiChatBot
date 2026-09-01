package com.mypack.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String Intent;
    //private String role;
    //private String timestamp;
    private String conversationId;
    //private Double confidence;
    private String message;
    private String response;

    public ChatResponse(Long conversationId, String intent, String message) {
    }
}
