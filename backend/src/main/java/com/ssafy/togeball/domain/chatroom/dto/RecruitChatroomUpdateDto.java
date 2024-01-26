package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecruitChatroomUpdateDto {

    private Integer id;
    private String title;
    private String description;
    private Integer capacity;
    private Integer gameId;
    private Integer cheeringClubId;
    private List<Integer> tagIds;
}
