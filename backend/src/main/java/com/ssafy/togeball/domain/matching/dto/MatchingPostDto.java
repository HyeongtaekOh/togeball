package com.ssafy.togeball.domain.matching.dto;

import com.ssafy.togeball.domain.matching.entity.Matching;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class MatchingPostDto {
    
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
