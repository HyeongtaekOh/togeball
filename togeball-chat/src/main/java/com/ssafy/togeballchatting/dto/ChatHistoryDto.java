package com.ssafy.togeballchatting.dto;

import com.ssafy.togeballchatting.entity.ChatHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class ChatHistoryDto {

    private Integer userId;
    private Integer roomId;
    private Instant firstMessageTimestamp;
    private Instant lastReadTimestamp;

    public static ChatHistoryDto of(ChatHistory chatHistory) {
        return ChatHistoryDto.builder()
                .userId(chatHistory.getUserId())
                .roomId(chatHistory.getRoomId())
                .firstMessageTimestamp(chatHistory.getFirstMessageTimestamp())
                .lastReadTimestamp(chatHistory.getLastReadTimestamp())
                .build();
    }

    public ChatHistory toEntity() {
        return ChatHistory.builder()
                .userId(userId)
                .roomId(roomId)
                .firstMessageTimestamp(firstMessageTimestamp)
                .lastReadTimestamp(lastReadTimestamp)
                .build();
    }
}
