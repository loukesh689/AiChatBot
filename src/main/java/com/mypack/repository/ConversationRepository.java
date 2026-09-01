package com.mypack.repository;

import com.mypack.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ConversationRepository extends JpaRepository<com.mypack.entity.Conversation, Long> {
    List<Conversation> findByUserIdOrderByUpdatedAtDesc(Long userId);

}
