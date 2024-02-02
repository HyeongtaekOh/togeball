package com.ssafy.togeballchatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 모든 IP에 응답을 허용
                .allowedMethods("*") // 모든 HTTP Method에 응답을 허용
                .allowedHeaders("*") // 모든 HTTP Header에 응답을 허용
                .allowCredentials(false) // 자격증명 허용
                .maxAge(3600L); // 3600초 동안 pre-flight 리퀘스트를 캐싱
    }
}
