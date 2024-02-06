package com.ssafy.togeball.domain.auth.controller;

import com.ssafy.togeball.domain.auth.dto.OAuth2LoginRequest;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/code")
    public ResponseEntity<?> handleAuthorizationCode(@RequestBody OAuth2LoginRequest request, HttpServletResponse response) {
        String authorizationCode = request.getCode();
        String provider = request.getProvider();

        Integer userId = authService.oauth2Login(provider, authorizationCode, response);
        User user = userService.findUserById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        return ResponseEntity.ok().body(UserResponse.of(user));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        authService.reissueAccessToken(request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<?> jwtTest() {
        return ResponseEntity.ok().body("jwt test 성공");
    }
}
