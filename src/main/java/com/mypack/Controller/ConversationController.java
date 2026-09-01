package com.mypack.Controller;

import com.mypack.dto.Request.RenameConversationRequest;
import com.mypack.dto.Response.ConversationResponse;
import com.mypack.dto.Response.MessageResponse;
import com.mypack.entity.Conversation;
import com.mypack.entity.Message;
import com.mypack.repository.MessageRepository;
import com.mypack.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {
    @Autowired
    ConversationService conversationService;
    @Autowired
    MessageRepository messageRepository;
    public ConversationController(
            ConversationService conversationService,
            MessageRepository messageRepository
    ) {
        this.conversationService = conversationService;
        this.messageRepository = messageRepository;
    }

    // Get all conversations of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationResponse>>
    getUserConversations(
            @PathVariable Long userId
    ) {

        List<ConversationResponse> conversations =
                conversationService
                        .getUserConversations(userId);

        List<ConversationResponse> response =
                conversations.stream()
                        .map(ConversationResponse::new)
                        .toList();

        return ResponseEntity.ok(response);
    }

    // Get one conversation
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse>
    getConversation(
            @PathVariable Long conversationId
    ) {

        Conversation conversation =
                conversationService
                        .getConversation(conversationId);

        return ResponseEntity.ok(
                new ConversationResponse(
                        conversation
                )
        );
    }

    // Get messages of conversation
    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<MessageResponse>>
    getMessages(
            @PathVariable Long conversationId
    ) {

        List<Message> messages =
                messageRepository
                        .findByConversationIdOrderByCreatedAtAsc(
                                conversationId
                        );

        List<MessageResponse> response =
                messages.stream()
                        .map(MessageResponse::new)
                        .toList();

        return ResponseEntity.ok(response);
    }

    // Rename conversation
    @PutMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse>
    renameConversation(
            @PathVariable Long conversationId,
            @Valid @RequestBody RenameConversationRequest request
    ) {

        Conversation conversation =
                conversationService.renameConversation(
                        conversationId,
                        request.getTitle()
                );

        return ResponseEntity.ok(
                new ConversationResponse(
                        conversation
                )
        );
    }

    // Delete conversation
    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void>
    deleteConversation(
            @PathVariable Long conversationId
    ) {

        conversationService.deleteConversation(
                conversationId
        );

        return ResponseEntity.noContent()
                .build();
    }

}
