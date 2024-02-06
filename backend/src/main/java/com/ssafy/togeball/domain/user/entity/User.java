package com.ssafy.togeball.domain.user.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.notice.entity.Notice;
import com.ssafy.togeball.domain.tag.entity.UserTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "TBL_USER")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime birthdate;

    private String phone;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<UserTag> userTags = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    public void changeClub(Club club) {
        this.club = club;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeGender(Gender gender) {
        this.gender = gender;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changeBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    @Builder
    public User(String email, String nickname, Gender gender, LocalDateTime birthdate, String phone, String profileImage, Club club, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.profileImage = profileImage;
        this.club = club;
        this.role = role;
    }
}
