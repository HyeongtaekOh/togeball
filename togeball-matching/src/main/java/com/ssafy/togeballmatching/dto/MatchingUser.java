package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class MatchingUser {

    private Integer userId;
    private String nickname;
    private String profileImage;
    private List<UserTag> tags;
}
