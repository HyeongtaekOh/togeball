package com.ssafy.togeballchatting.repository;

import com.ssafy.togeballchatting.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findAllByRoomIdAndTimestampIsGreaterThanEqual(Integer roomId, Instant timestamp, Pageable pageable);
}
