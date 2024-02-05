package com.ssafy.togeball.domain.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )
<<<<<<< 367f7c572c5792a8857d3d47b63b815fcae741b5
                .exposedHeaders("Authorization")
                .exposedHeaders("Authorization-refresh")
=======
                .exposedHeaders("Authorization", "Authorization-refresh")
>>>>>>> 8f81cd16385d5c8fe441b42fa11946de14e2400e
                .maxAge(3600);
    }
}
