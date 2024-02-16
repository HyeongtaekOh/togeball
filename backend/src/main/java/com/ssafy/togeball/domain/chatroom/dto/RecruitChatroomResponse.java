package com.ssafy.togeball.domain.chatroom.dto;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class RecruitChatroomResponse extends ChatroomResponse {

    private UserResponse manager;
    private GameResponse game;
    private ClubResponse cheeringClub;
    private String description;
    private List<TagResponse> tags;

    public static RecruitChatroomResponse of(RecruitChatroom chatroom) {

        List<TagResponse> tags = chatroom.getRecruitTags().stream()
                .map(recruitTag -> TagResponse.of(recruitTag.getTag()))
                .toList();

        RecruitChatroomResponse response = RecruitChatroomResponse.builder()
                .id(chatroom.getId())
                .type(chatroom.getType())
                .title(chatroom.getTitle())
                .manager(UserResponse.of(chatroom.getManager()))
                .members(chatroom.getChatroomMemberships().stream()
                        .map(chatroomMembership -> UserResponse.of(chatroomMembership.getUser()))
                        .toList())
                .game(GameResponse.of(chatroom.getGame()))
                .cheeringClub(ClubResponse.of(chatroom.getCheeringClub()))
                .description(chatroom.getDescription())
                .capacity(chatroom.getCapacity())
                .tags(tags)
                .build();

        return response;
    }
}
