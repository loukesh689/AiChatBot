package com.mypack.Controller;

import com.mypack.dto.Request.ChatRequest;
import com.mypack.dto.Response.ChatResponse;
import com.mypack.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> processMessage(@RequestBody ChatRequest chatRequest) {
        ChatResponse response = chatService.processMessage(chatRequest);
        return ResponseEntity.ok(response);
    }
}