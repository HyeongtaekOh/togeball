package com.ssafy.togeball.domain.user.controller;

import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.tag.dto.TagIdsRequest;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest, HttpServletResponse response) {
        Integer userId = userService.signUp(userSignUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();
        authService.setTokensForUser(userId, response);
        return ResponseEntity.created(location).body(Map.of("id", userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable(name = "userId") Integer userId) {
        User user = userService.findUserById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = UserResponse.of(user);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{userId}/tags")
    public ResponseEntity<?> updateUserTags(@PathVariable(name = "userId") Integer userId, @RequestBody TagIdsRequest tagIdsRequest) {
        userService.updateUserTags(userId, tagIdsRequest.getTagIds());
        return ResponseEntity.noContent().build();
    }
}
