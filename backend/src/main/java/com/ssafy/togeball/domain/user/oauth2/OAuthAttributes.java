package com.ssafy.togeball.domain.user.oauth2;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.entity.SocialType;
import com.ssafy.togeball.domain.auth.exception.InvalidSocialTypeException;
import com.ssafy.togeball.domain.user.entity.Role;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.common.utils.PasswordUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    private OAuthAttributes (String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        if (socialType == SocialType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        throw new InvalidSocialTypeException();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public User toUserEntity(OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .email(oauth2UserInfo.getEmail())
                .nickname("Guest#" + PasswordUtil.generateRandomPassword())
                .profileImage(oauth2UserInfo.getImageUrl())
                .role(Role.GUEST)
                .build();
    }

    public Auth toAuthEntity(SocialType socialType, Integer id, OAuth2UserInfo oauth2UserInfo) {
        return Auth.builder()
                .userId(id)
                .password(PasswordUtil.generateRandomPassword())
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .build();
    }
}
