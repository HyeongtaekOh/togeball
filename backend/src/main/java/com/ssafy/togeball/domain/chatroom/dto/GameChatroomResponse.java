package com.ssafy.togeball.domain.chatroom.dto;

import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class GameChatroomResponse extends ChatroomResponse {

    private GameResponse game;

    public static GameChatroomResponse of(GameChatroom chatroom) {

        GameChatroomResponse response = GameChatroomResponse.builder()
                .id(chatroom.getId())
                .type(chatroom.getType())
                .title(chatroom.getTitle())
                .game(GameResponse.of(chatroom.getGame()))
                .build();
        return response;
    }
}
