package com.ssafy.togeballmatching.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TagResponse {

    private Integer id;
    private String content;
    private TagType type;
}
