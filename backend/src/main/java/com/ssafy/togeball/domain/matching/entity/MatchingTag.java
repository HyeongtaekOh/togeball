package com.ssafy.togeball.domain.matching.entity;

import com.ssafy.togeball.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_MATCHING_TAG", uniqueConstraints = {
        @UniqueConstraint(name = "matching_tag_uk", columnNames = {"matching_id", "tag_id"})
    }, indexes = {
    @Index(name = "matching_tag_matching_idx", columnList = "matching_id")
})
public class MatchingTag {

    @Id
    @Column(name = "matching_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public MatchingTag(Matching matching, Tag tag) {
        this.matching = matching;
        this.tag = tag;
    }
}
