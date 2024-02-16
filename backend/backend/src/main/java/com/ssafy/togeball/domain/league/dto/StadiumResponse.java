package com.ssafy.togeball.domain.league.dto;

import com.ssafy.togeball.domain.league.entity.Stadium;
import lombok.Builder;

@Builder
public class StadiumResponse {

    private Integer id;
    private String name;

    public static StadiumResponse of(Stadium stadium) {
        return StadiumResponse.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .build();
    }
}
