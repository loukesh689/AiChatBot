package com.mypack.dto.Response;

import com.mypack.entity.Conversation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public ConversationResponse(Conversation conversation) {
    }

    public ConversationResponse(ConversationResponse conversationResponse) {
    }
}
