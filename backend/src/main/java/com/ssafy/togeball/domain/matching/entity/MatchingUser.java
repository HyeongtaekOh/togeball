package com.ssafy.togeball.domain.matching.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TBL_MATCHING_USER", indexes = {
    @Index(name = "matching_user_matching_idx", columnList = "matching_id")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_matching_user", columnNames = {"matching_id", "user_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_user_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MatchingUser(Matching matching, User user) {
        this.matching = matching;
        this.user = user;
    }
}
