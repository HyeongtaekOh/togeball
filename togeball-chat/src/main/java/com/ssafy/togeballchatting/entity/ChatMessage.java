package com.ssafy.togeballchatting.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Builder
@ToString
@Document(collection = "chatMessage")
public class ChatMessage {

    @Id
    private String id;

    private Integer roomId;
    private Integer senderId;
    private MessageType type;
    private String content;

    @CreatedDate
    private Instant timestamp;
}
