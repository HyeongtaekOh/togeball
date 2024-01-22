package com.ssafy.togeball.domain.notice.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @ToString
@Table(name = "TBL_NOTICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Todo: matching 엔티티와 연결
    @Column(name = "matching_id")
    private Long matchingId;

    @Builder
    public Notice(User user, Long matchingId) {
        this.user = user;
        this.matchingId = matchingId;
    }
}
