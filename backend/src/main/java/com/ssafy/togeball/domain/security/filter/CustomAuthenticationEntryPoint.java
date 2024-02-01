package com.ssafy.togeball.domain.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
        AuthenticationEntryPoint : 인증이 되지 않은 사용자
        - 존재하지 않는 엔드 포인트라고 해도 드러내지 않기 위해 404를 사용하지 않는다.
        - 401 (Unauthorized)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
