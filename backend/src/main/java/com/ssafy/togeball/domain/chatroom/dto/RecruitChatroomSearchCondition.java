package com.ssafy.togeball.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RecruitChatroomSearchCondition {

    private String keyword;
    private Integer gameId;
    private Integer cheeringClubId;
    private Integer managerId;
    private List<Integer> tagIds;
}
