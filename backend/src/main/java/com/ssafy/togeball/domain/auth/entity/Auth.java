package com.ssafy.togeball.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @Setter
    private String refreshToken;

    @Builder
    public Auth(Integer userId, String email, String refreshToken, SocialType socialType, String socialId) {
        this.userId = userId;
        this.email = email;
        this.refreshToken = refreshToken;
        this.socialType = socialType;
        this.socialId = socialId;
    }
}
