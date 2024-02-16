package com.ssafy.togeball.domain.auth.service;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.entity.SocialType;
import com.ssafy.togeball.domain.auth.exception.InvalidSocialTypeException;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.oauth2.CustomOAuth2User;
import com.ssafy.togeball.domain.user.oauth2.OAuthAttributes;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private static final String GOOGLE = "google";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User attribute : {}",oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        User createdUser = getUser(extractAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getId(),
                createdUser.getRole()
        );
    }

    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        Auth find = authRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getOAuth2UserInfo().getId()).orElse(null);

        if (find == null) {
            User user = saveUser(attributes);
            find = saveAuth(attributes, socialType, user.getId());
        }

        return userRepository.findById(find.getUserId()).orElseThrow(UserNotFoundException::new);
    }

    private SocialType getSocialType(String registrationId) {
        if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        else if (GOOGLE.equals(registrationId)) {
            return SocialType.GOOGLE;
        }
        throw new InvalidSocialTypeException();
    }

    private User saveUser(OAuthAttributes attributes) {
        log.info("처음 오시는 고객님!");
        User createdUser = attributes.toUserEntity(attributes.getOAuth2UserInfo());
        return userRepository.save(createdUser);
    }

    private Auth saveAuth(OAuthAttributes attributes, SocialType socialType, Integer userId) {
        Auth createdAuth = attributes.toAuthEntity(socialType, userId, attributes.getOAuth2UserInfo());
        return authRepository.save(createdAuth);
    }
}
