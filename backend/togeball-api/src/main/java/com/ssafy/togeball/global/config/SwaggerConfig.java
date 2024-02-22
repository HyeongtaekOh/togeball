package com.ssafy.togeball.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "Togeball API Docs",
                description = "투게볼의 RESTful API입니다.",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    private static final String BEARER = "bearer";
    private static final String SECURITY_JWT_NAME = "JWT";
    private static final String ACCESS_TOKEN_SUBJECT = "Access Token (Bearer)";

    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(ACCESS_TOKEN_SUBJECT);

        SecurityScheme accessTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER)
                .bearerFormat(SECURITY_JWT_NAME)
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Components components = new Components()
                .addSecuritySchemes(ACCESS_TOKEN_SUBJECT, accessTokenScheme);

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
