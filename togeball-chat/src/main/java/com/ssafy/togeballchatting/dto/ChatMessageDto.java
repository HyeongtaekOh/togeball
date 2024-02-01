package com.ssafy.togeballchatting.dto;

import com.ssafy.togeballchatting.entity.ChatMessage;
import com.ssafy.togeballchatting.entity.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class ChatMessageDto {

        private Integer senderId;
        private Integer roomId;
        private MessageType type;
        private String content;
        private Instant timestamp;

        public static ChatMessageDto of(ChatMessage chatMessage) {
                return ChatMessageDto.builder()
                        .senderId(chatMessage.getSenderId())
                        .roomId(chatMessage.getRoomId())
                        .type(chatMessage.getType())
                        .content(chatMessage.getContent())
                        .timestamp(chatMessage.getTimestamp())
                        .build();
        }

        public ChatMessage toEntity() {
                return ChatMessage.builder()
                        .senderId(senderId)
                        .roomId(roomId)
                        .type(type)
                        .content(content)
                        .timestamp(timestamp)
                        .build();
        }
}
