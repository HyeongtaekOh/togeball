package com.ssafy.togeball.domain.user.controller;

import com.ssafy.togeball.domain.auth.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception {
        try {
            userService.signUp(userSignUpRequest);
            return ResponseEntity.ok().body("회원 가입");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 회원입니다.");
        }
    }

    @GetMapping("/jwt-test")
    public ResponseEntity<?> jwtTest() {
        return ResponseEntity.ok().body("jwt test 성공");
    }
}