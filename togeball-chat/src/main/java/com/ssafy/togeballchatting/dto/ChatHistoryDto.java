package com.ssafy.togeballchatting.dto;

import com.ssafy.togeballchatting.entity.ChatHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class ChatHistoryDto {

    private String id;
    private Integer userId;
    private Integer roomId;
    private Instant enteredTimestamp;

    @Setter
    private Instant lastReadTimestamp;

    public static ChatHistoryDto of(ChatHistory chatHistory) {
        return ChatHistoryDto.builder()
                .id(chatHistory.getId())
                .userId(chatHistory.getUserId())
                .roomId(chatHistory.getRoomId())
                .enteredTimestamp(chatHistory.getEnteredTimestamp())
                .lastReadTimestamp(chatHistory.getLastReadTimestamp())
                .build();
    }

    public ChatHistory toEntity() {
        return ChatHistory.builder()
                .id(id)
                .userId(userId)
                .roomId(roomId)
                .enteredTimestamp(enteredTimestamp)
                .lastReadTimestamp(lastReadTimestamp)
                .build();
    }
}
