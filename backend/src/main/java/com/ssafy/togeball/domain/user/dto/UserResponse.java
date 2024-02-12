package com.ssafy.togeball.domain.user.dto;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.user.entity.Gender;
import com.ssafy.togeball.domain.user.entity.User;
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
    private String nickname;
    private Gender gender;
    private LocalDateTime birthdate;
    private String profileImage;
    private List<TagResponse> tags;
    private String clubSponsorName;
    private String clubName;
    private String clubLogo;

    public static UserResponse of(User user) {

        List<TagResponse> tags = user.getUserTags().stream()
                .map(userTag -> TagResponse.of(userTag.getTag()))
                .toList();

        Club club = user.getClub();

        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birthdate(user.getBirthdate())
                .profileImage(user.getProfileImage())
                .tags(tags)
                .clubSponsorName(club != null ? club.getSponsorName() : null)
                .clubName(club != null ? club.getClubName() : null)
                .clubLogo(club != null ? club.getLogo() : null)
                .build();
    }
}
