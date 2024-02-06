package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MatchingRequest {

    private String title;
    private Integer capacity;
    private List<Integer> userIds;
    private List<Integer> tagIds;
}
