package com.ssafy.togeball.domain.tag.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class TagIdsRequest {
    private Set<Integer> tagIds;
}
