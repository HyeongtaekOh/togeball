package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RecruitChatroomRequest {

    @Setter
    private Integer id;
    private String title;
    private String description;
    private Integer capacity;
    private Integer managerId;
    private Integer gameId;
    private Integer cheeringClubId;
    private List<Integer> tagIds;
}
