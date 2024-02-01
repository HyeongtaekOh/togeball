package com.ssafy.togeball.domain.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HttpRequestEndpointChecker httpRequestEndpointChecker;

    /*
        AccessDeniedHandler : 인증은 되었지만 (유효한 AccessToken) 접근하기에 부족한 권한
        - 존재하지 않는 엔드 포인트 : 404 (Not Found)
        - 이외 : 403 (Forbidden)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        if (!httpRequestEndpointChecker.isEndpointExist(request)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
