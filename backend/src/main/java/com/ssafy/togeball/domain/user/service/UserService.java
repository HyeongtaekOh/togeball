package com.ssafy.togeball.domain.user.service;

import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.exception.ClubNotFoundException;
import com.ssafy.togeball.domain.league.service.LeagueService;
import com.ssafy.togeball.domain.user.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.tag.service.TagService;
import com.ssafy.togeball.domain.user.dto.UserUpdateRequest;
import com.ssafy.togeball.domain.user.entity.Role;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final TagService tagService;
    private final LeagueService leagueService;

    public Integer signUp(UserSignUpRequest userSignUpRequest) {

        if (userRepository.findByEmail(userSignUpRequest.getEmail()).isPresent()) {
            throw new ApiException(UserErrorCode.CONFLICT_EMAIL);
        }

        if (userRepository.findByNickname(userSignUpRequest.getNickname()).isPresent()) {
            throw new ApiException(UserErrorCode.CONFLICT_NICKNAME);
        }

        User user = User.builder()
                .email(userSignUpRequest.getEmail())
                .nickname(userSignUpRequest.getNickname())
                .role(Role.BASIC)
                .build();

        Integer userId = userRepository.save(user).getId();
        String encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword());

        authService.createAuth(userId, encodedPassword);
        return userId;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Integer userId) {
        return userRepository.findUserWithTagsById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public void updateUserTags(Integer userId, Set<Integer> tagIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        tagService.updateUserTags(user, tagIds);
    }

    @Transactional
    public void checkEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ApiException(UserErrorCode.CONFLICT_EMAIL);
        }
    }

    @Transactional
    public void checkNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new ApiException(UserErrorCode.CONFLICT_NICKNAME);
        }
    }

    @Transactional
    public void updateUser(Integer userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (userUpdateRequest.getNickname() != null) {
            User tmpUser = userRepository.findByNickname(userUpdateRequest.getNickname()).orElse(null);
            if (tmpUser != null && !tmpUser.getId().equals(userId))
                throw new ApiException(UserErrorCode.CONFLICT_NICKNAME);
            user.changeNickname(userUpdateRequest.getNickname());
        }

        if (userUpdateRequest.getGender() != null) {
            user.changeGender(userUpdateRequest.getGender());
        }

        if (userUpdateRequest.getBirthdate() != null) {
            user.changeBirthdate(userUpdateRequest.getBirthdate());
        }

        if (userUpdateRequest.getProfileImage() != null) {
            user.changeProfileImage(userUpdateRequest.getProfileImage());
        }

        if (userUpdateRequest.getClubId() != null) {
            Club club = leagueService.findById(userUpdateRequest.getClubId());
            user.changeClub(club);
        }

        if (!userUpdateRequest.getTagIds().isEmpty()) {
            tagService.updateUserTags(user, userUpdateRequest.getTagIds());
        }

        userRepository.save(user);
    }

    @Transactional
    public void upgradeRole(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.changeRole(Role.BASIC);
        userRepository.save(user);
    }
}