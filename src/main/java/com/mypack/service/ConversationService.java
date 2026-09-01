package com.mypack.service;

import com.mypack.dto.Response.ConversationResponse;
import com.mypack.entity.Conversation;
import com.mypack.entity.User;
import com.mypack.repository.ConversationRepository;
import com.mypack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired

    UserRepository userRepository;
    public Conversation getOrCreateConversation(
            Long userId,
            Long ConversationId,
            String firstMessage
    ){
        if(ConversationId!=null){
            return conversationRepository.findById(ConversationId).orElseThrow(()->new RuntimeException("Conversation not found"+ConversationId));
    }
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"+userId));
        String Title= createTitle(firstMessage);
        Conversation conversation=new Conversation(Title,user);
        conversationRepository.save(conversation);
        return conversation;

}
// get all Conversation of user
    public List<Conversation> getUserConversation(Long userId){
        return conversationRepository
                .findByUserIdOrderByUpdatedAtDesc(userId);
    }
    // get single conversation
    public Conversation getConversation(Long conversationId){
        return conversationRepository.findById(conversationId)
                .orElseThrow(()->new RuntimeException("Conversation not found"+conversationId));
    }
    // rename conversation
    @Transactional
    public Conversation renameConversation(
            Long conversationId,
            String newTitle
    ) {
        Conversation conversation = getConversation(conversationId);
        if (newTitle == null || newTitle.isBlank()) {
            throw new RuntimeException("Conversation title cannot be empty");
        }
        conversation.setTitle(newTitle.trim());
        return conversationRepository.save(conversation);
    }
   // delete Conversation
   @Transactional
   public void deleteConversation(Long conversationId){
    Conversation conversation=getConversation(conversationId);
    conversationRepository.delete(conversation);
   }

private String createTitle (String message) {
    if (message != null || message.isBlank()) {
        return "new cnversation ";
    }
    String cleanmessage = message.trim();
    if (cleanmessage.length() <= 50) {
        return cleanmessage;
    }
    return cleanmessage.substring(0, 50);

    }
    public List<ConversationResponse> getUserConversations(Long userId) {
        // Aapka logic (e.g., repository se conversations fetch karna)
        List<Conversation> conversations = conversationRepository.findByUserIdOrderByUpdatedAtDesc(userId);

        return conversations.stream()
                .map(ConversationResponse::new)
                .collect(Collectors.toList());
    }
}
