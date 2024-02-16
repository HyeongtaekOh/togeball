package com.ssafy.togeballchatting.repository;

import com.ssafy.togeballchatting.entity.ChatMessage;
import com.ssafy.togeballchatting.entity.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findAllByRoomIdOrderByTimestampDesc(Integer roomId, Pageable pageable);

    Page<ChatMessage> findAllByRoomIdAndTimestampIsGreaterThanEqualOrderByTimestampDesc(Integer roomId, Instant timestamp, Pageable pageable);

    Integer countByRoomIdAndTypeNotAndTimestampIsGreaterThan(Integer roomId, MessageType type, Instant lastReadTimestamp);

    ChatMessage findTopByRoomIdAndTypeNotAndTimestampIsGreaterThanEqualOrderByTimestampDesc(Integer roomId, MessageType type, Instant timestamp);
}
