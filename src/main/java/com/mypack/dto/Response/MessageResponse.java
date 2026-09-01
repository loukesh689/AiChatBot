package com.mypack.dto.Response;

import com.mypack.entity.Message;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private String role;
    private String content;
    private LocalDateTime createdAt;

    public MessageResponse(Message message) {
    }
}
