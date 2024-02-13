package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class MatchingRequest {

    private String title;
    private Integer capacity;
    private List<Integer> userIds;
    private Set<Integer> tagIds;
}
