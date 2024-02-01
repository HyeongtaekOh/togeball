package com.ssafy.togeball.domain.tag.dto;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
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
