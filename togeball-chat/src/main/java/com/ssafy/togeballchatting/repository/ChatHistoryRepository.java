package com.ssafy.togeballchatting.repository;

import com.ssafy.togeballchatting.entity.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatHistoryRepository extends MongoRepository<ChatHistory, String> {
    Optional<ChatHistory> findByRoomIdAndUserId(Integer roomId, Integer userId);

    void deleteByRoomIdAndUserId(Integer roomId, Integer userId);
}
