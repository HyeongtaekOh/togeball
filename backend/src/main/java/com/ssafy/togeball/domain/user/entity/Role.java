package com.ssafy.togeball.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // guest : 프로필 설정 전 회원, basic : 회원, verified : 본인 인증 완료 회원
    GUEST("ROLE_GUEST"), BASIC("ROLE_BASIC"), VERIFIED("ROLE_VERIFIED"), ADMIN("ROLE_ADMIN");

    private final String key;
}