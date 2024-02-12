package com.ssafy.togeballmatching.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Tag {

    private Integer id;
    private String content;
    private TagType type;
}
