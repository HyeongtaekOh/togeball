package com.ssafy.togeball.domain.tag.dto;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import lombok.Data;

@Data
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
