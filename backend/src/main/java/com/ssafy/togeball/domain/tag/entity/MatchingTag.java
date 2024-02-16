package com.ssafy.togeball.domain.tag.entity;

import com.ssafy.togeball.domain.common.entity.AbstractJoinEntity;
import com.ssafy.togeball.domain.common.utils.SimpleTuple;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_MATCHING_TAG", uniqueConstraints = {
        @UniqueConstraint(name = "uk_matching_tag", columnNames = {"matching_id", "tag_id"})
    }, indexes = {
    @Index(name = "matching_tag_matching_idx", columnList = "matching_id")
})
public class MatchingTag extends AbstractJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_tag_id")
    private Integer id;

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

    @Override
    protected Object getKey() {
        return new SimpleTuple<Integer, Integer>(matching.getId(), tag.getId());
    }
}
