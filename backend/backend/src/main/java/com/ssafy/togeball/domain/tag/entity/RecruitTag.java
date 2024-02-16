package com.ssafy.togeball.domain.tag.entity;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TBL_RECRUIT_TAG", uniqueConstraints = {
    @UniqueConstraint(name = "uk_recruit_tag", columnNames = {"chatroom_id", "tag_id"})
    }, indexes = {
    @Index(name = "recruit_tag_recruit_idx", columnList = "chatroom_id"),
    @Index(name = "recruit_tag_tag_idx", columnList = "tag_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_tag_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private RecruitChatroom recruitChatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public RecruitTag(RecruitChatroom recruitChatroom, Tag tag) {
        this.recruitChatroom = recruitChatroom;
        this.tag = tag;
    }
}
