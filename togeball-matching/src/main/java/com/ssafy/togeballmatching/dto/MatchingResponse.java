package com.ssafy.togeballmatching.dto;

import java.util.List;

public class MatchingResponse {

    private Integer id;
    private Integer matchingChatroomId;
    private String title;
    private Integer capacity;
    private List<UserResponse> users;
    private List<TagResponse> tags;

}
