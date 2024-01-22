package com.ssafy.togeball.domain.user.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "TBL_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime birthdate;

    private String phone;

    private String profileImage;

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePersonalInfo(Gender gender, LocalDateTime birthdate, String phone) {
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
    }

    @Builder
    public User(String email, String password, String nickname, Gender gender, LocalDateTime birthdate, String phone, String profileImage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.profileImage = profileImage;
    }
}
