package com.ssafy.togeball.domain.user.dto;

import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserUpdateRequest {
    private String nickname = null;
    private String profileImage = null;
    private Integer clubId = null;
    private Gender gender = null;
    private LocalDateTime birthdate = null;
    private Role role = null;
    private Set<Integer> tagIds = new HashSet<>();
}
