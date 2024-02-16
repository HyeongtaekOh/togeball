package com.ssafy.togeball.domain.tag.dto;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
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

    public static TagResponse of(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .content(tag.getContent())
                .type(tag.getType())
                .build();
    }
}
