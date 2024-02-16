package com.ssafy.togeball.domain.matching.dto;

import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MatchingResponse {

    private Integer id;
    private Integer matchingChatroomId;
    private String title;
    private Integer capacity;
    private List<UserResponse> users;
    private List<TagResponse> tags;

    public static MatchingResponse of(Matching matching) {

        List<TagResponse> tags = matching.getMatchingTags().stream()
                .map(matchingTag -> TagResponse.of(matchingTag.getTag()))
                .toList();
        List<UserResponse> users = matching.getMatchingUsers().stream()
                .map(matchingUser -> UserResponse.of(matchingUser.getUser()))
                .toList();
        return MatchingResponse.builder()
                .id(matching.getId())
                .matchingChatroomId(matching.getMatchingChatroom().getId())
                .title(matching.getTitle())
                .capacity(matching.getCapacity())
                .users(users)
                .tags(tags)
                .build();
    }
}
