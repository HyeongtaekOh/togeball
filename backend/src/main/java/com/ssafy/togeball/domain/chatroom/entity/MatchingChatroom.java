package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.matching.entity.Matching;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TBL_MATCHINGCHATROOM")
@DiscriminatorValue("MATCHING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingChatroom extends Chatroom {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "matching_id", nullable = false)
    private Matching matching;

    @Builder
    public MatchingChatroom(String title, Matching matching) {
        this.title = title;
        this.matching = matching;
    }
}
