package com.ssafy.togeballmatching.dto;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserTag {

    private Integer tagId;
    private String content;
    private TagType tagType;

    public enum TagType {
        PREFERRED_TEAM,
        PREFERRED_STADIUM,
        PREFERRED_SEAT,
        CHEERING_STYLE,
        MBTI,
        SEASON_PASS,
        UNLABELED,
        CUSTOM
    }
}
