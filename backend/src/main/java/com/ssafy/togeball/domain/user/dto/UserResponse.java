package com.ssafy.togeball.domain.user.dto;

import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {

    private Integer id;
    private String email;
    private String nickname;
    private Gender gender;
    private LocalDateTime birthdate;
    private String phone;
    private String profileImage;
    private List<TagResponse> tags;

    public static UserResponse of(User user) {

        List<TagResponse> tags = user.getUserTags().stream()
                .map(userTag -> TagResponse.of(userTag.getTag()))
                .toList();
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birthdate(user.getBirthdate())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .tags(tags)
                .build();
    }
}
