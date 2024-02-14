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

        private String id;
        private Integer roomId;
        private Integer senderId;
        private String nickname;
        private MessageType type;
        private String content;
        private Instant timestamp;

        public static ChatMessageDto of(ChatMessage chatMessage) {
                return ChatMessageDto.builder()
                        .id(chatMessage.getId())
                        .roomId(chatMessage.getRoomId())
                        .senderId(chatMessage.getSenderId())
                        .nickname(chatMessage.getNickname())
                        .type(chatMessage.getType())
                        .content(chatMessage.getContent())
                        .timestamp(chatMessage.getTimestamp())
                        .build();
        }

        public ChatMessage toEntity() {
                return ChatMessage.builder()
                        .id(id)
                        .roomId(roomId)
                        .senderId(senderId)
                        .nickname(nickname)
                        .type(type)
                        .content(content)
                        .timestamp(timestamp)
                        .build();
        }
}
