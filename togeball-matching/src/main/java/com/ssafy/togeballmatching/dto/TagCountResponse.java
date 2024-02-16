package com.ssafy.togeballmatching.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@Value
public class TagCountResponse {

    private Integer id;
    private String content;
    private TagType type;
    private int count;

    public static TagCountResponse of(Tag tag, int count) {
        return TagCountResponse.builder()
                .id(tag.getId())
                .content(tag.getContent())
                .type(tag.getType())
                .count(count)
                .build();
    }
}
