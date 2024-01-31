package com.ssafy.togeball.domain.user.controller;

import com.ssafy.togeball.domain.auth.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception {
        try {
            Integer userId = userService.signUp(userSignUpRequest);
            return ResponseEntity.ok().body(Map.of("id", userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> jwtTest() {
        return ResponseEntity.ok().body("jwt test 성공");
    }
}
