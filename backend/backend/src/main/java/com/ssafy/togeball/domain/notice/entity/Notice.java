package com.ssafy.togeball.domain.notice.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "TBL_NOTICE")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //알림을 받을 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id", nullable = false)
    private Matching matching;

    private boolean isRead = false;

    @Builder
    public Notice(User user, Matching matching, Boolean isRead) {
        this.user = user;
        this.matching = matching;
        this.isRead = isRead;
    }

    public void read() {
        this.isRead = true;
    }
}
