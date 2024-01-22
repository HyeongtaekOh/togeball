package com.ssafy.togeball.domain.tag.entity;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void tagBuilderTest() {
        Tag tag = Tag.builder()
                .content("롯데")
                .type(TagType.PREFERRED_TEAM)
                .build();

        System.out.println("tag.content : " + tag.getContent());
        System.out.println("tag.type : " + tag.getType());
    }
}
