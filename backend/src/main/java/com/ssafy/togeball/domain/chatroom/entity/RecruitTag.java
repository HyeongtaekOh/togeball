package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_RECRUIT_TAG", uniqueConstraints = {
    @UniqueConstraint(name = "recruit_tag_uk", columnNames = {"chatroom_id", "tag_id"})
    }, indexes = {
    @Index(name = "recruit_tag_recruit_idx", columnList = "chatroom_id"),
    @Index(name = "recruit_tag_tag_idx", columnList = "tag_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private RecruitChatroom recruitChatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    private RecruitTag(RecruitChatroom recruitChatroom, Tag tag) {
        this.recruitChatroom = recruitChatroom;
        this.tag = tag;
        recruitChatroom.addRecruitTag(this);
    }

    public static RecruitTag createRecruitTag(RecruitChatroom recruitChatroom, Tag tag) {
        return RecruitTag.builder()
            .recruitChatroom(recruitChatroom)
            .tag(tag)
            .build();
    }
}
