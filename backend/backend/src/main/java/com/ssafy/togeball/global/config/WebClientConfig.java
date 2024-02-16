package com.ssafy.togeball.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://i10a610.p.ssafy.io:8080")
//                .baseUrl("http://localhost:8080")
                .build();
    }
}
