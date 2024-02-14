package com.ssafy.togeballmatching.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString(of = {"userId"})
@EqualsAndHashCode
public class MatchingUser {

    @JsonProperty("id")
    private Integer userId;
    private String nickname;
    private String profileImage;
    private List<Tag> tags;
}
