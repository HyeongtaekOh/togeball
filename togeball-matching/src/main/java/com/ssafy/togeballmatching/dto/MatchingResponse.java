package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MatchingResponse {

    private Integer id;
    private Integer matchingChatroomId;
    private String title;
    private Integer capacity;
    private List<UserResponse> users;
    private List<TagResponse> tags;
}
