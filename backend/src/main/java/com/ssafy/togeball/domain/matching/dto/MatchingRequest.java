package com.ssafy.togeball.domain.matching.dto;

import com.ssafy.togeball.domain.matching.entity.Matching;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class MatchingRequest {
    
    private String title;
    private Integer capacity;
    private List<Integer> userIds;
    private List<Integer> tagIds;

    public Matching toEntity() {
        return Matching.builder()
                .title(title)
                .capacity(capacity)
                .build();
    }
}
