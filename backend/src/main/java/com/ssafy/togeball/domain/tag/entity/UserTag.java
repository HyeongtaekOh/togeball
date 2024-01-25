package com.ssafy.togeball.domain.tag.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TBL_USER_TAG", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_tag", columnNames = {"user_id", "tag_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_tag_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserTag(Tag tag, User user) {
        this.tag = tag;
        this.user = user;
    }
}
