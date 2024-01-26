package com.ssafy.togeball.domain.tag.dto;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateRequest {

    private String content;
    private TagType type;

    public Tag toEntity() {
        return Tag.builder()
                .content(content)
                .type(type)
                .build();
    }
}
