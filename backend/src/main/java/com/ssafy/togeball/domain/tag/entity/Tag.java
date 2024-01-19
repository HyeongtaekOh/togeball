package com.ssafy.togeball.domain.tag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagType type;

    @OneToMany(mappedBy = "tag")
    private List<UserTag> userTags;

    public void changeType(TagType type) {
        this.type = type;
    }

    @Builder
    public Tag(String content, TagType type) {
        this.content = content;
        this.type = type;
    }
}
