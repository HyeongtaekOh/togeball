package com.ssafy.togeball.domain.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestEndpointChecker {

    private final DispatcherServlet servlet;

    public boolean isEndpointExist(HttpServletRequest request) {

        if (servlet.getHandlerMappings() == null) return false;
        log.info("servlet.getHandlerMappings : {}", servlet.getHandlerMappings());

        for (HandlerMapping handlerMapping : servlet.getHandlerMappings()) {
            try {
                HandlerExecutionChain foundHandler = handlerMapping.getHandler(request);
                if (foundHandler != null) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}