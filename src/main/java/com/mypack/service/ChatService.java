package com.mypack.service;

import com.mypack.dto.Request.ChatRequest;
import com.mypack.dto.Response.ChatResponse;
import com.mypack.entity.Conversation;
import com.mypack.entity.Message;
import com.mypack.llm.LlmClient;
import com.mypack.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private IntentService intentService;
    @Autowired
    private PromptService promptService;
    @Autowired
    private LlmClient llmClient;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private MemoryService memoryService;

    public ChatResponse processMessage(ChatRequest chatRequest) {
        // 1. Null check on incoming request
        if (chatRequest == null || chatRequest.getMessage() == null || chatRequest.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }

        // Get/Create conversation
        Conversation conversation = conversationService.getOrCreateConversation(
                chatRequest.getUserId(),
                chatRequest.getConversationId(),
                chatRequest.getMessage()
        );

        // Save User Message
        Message userMessage = new Message();
        userMessage.setContent(chatRequest.getMessage()); // Directly set content using setter
        userMessage.setRole(Message.Role.USER);
        userMessage.setConversation(conversation);
        messageRepository.save(userMessage);

        // Get previous history
        List<Message> history = memoryService.getConversationHistory(conversation.getId());

        // Identify intent & build prompt
        String intent = intentService.identifyIntent(chatRequest.getMessage());
        String prompt = promptService.buildPrompt(chatRequest.getMessage(), intent, history);

        // Call LLM API
        String aiResponse = llmClient.generateResponse(prompt);
        if (aiResponse == null) {
            aiResponse = "Sorry, I couldn't generate a response at this moment.";
        }

        // Save AI Message
        Message assistantMessage = new Message();
        assistantMessage.setContent(aiResponse); // Directly set content using setter
        assistantMessage.setRole(Message.Role.ASSISTANT);
        assistantMessage.setConversation(conversation);
        messageRepository.save(assistantMessage);

        // Create Final Response
        return responseService.createChatResponse(
                conversation.getId(),
                intent,
                aiResponse
        );
    }
}