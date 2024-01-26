package com.ssafy.togeball.domain.league.dto;

import com.ssafy.togeball.domain.league.entity.Stadium;
import lombok.Builder;
import lombok.Data;

@Builder
public class StadiumResponse {

    private Integer stadiumId;
    private String name;

    public static StadiumResponse of(Stadium stadium) {
        return StadiumResponse.builder()
                .stadiumId(stadium.getId())
                .name(stadium.getName())
                .build();
    }
}
