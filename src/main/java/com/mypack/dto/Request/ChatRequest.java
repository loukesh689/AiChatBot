package com.mypack.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private Long conversationId;
    private Long UserId;
    @NotBlank(message = "message Cannot be empty")
    @Size(max = 4000,message = "message cannot exceed 4000 characters")
    private String message;
    //private String context;
    //private String intent;
}
