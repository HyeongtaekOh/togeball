package com.ssafy.togeball.domain.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/api/auth/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String USERNAME_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper, UserService userService) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            log.info("AuthenticationServiceException");
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<?, ?> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String email = (String) usernamePasswordMap.get(USERNAME_KEY);
        String password = (String) usernamePasswordMap.get(PASSWORD_KEY);


        Optional<User> userOptional = userService.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getId(), password);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            throw new UserNotFoundException();
        }
    }
}
