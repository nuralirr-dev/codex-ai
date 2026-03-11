package com.example.apidocsui.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    RestClient externalApiDocsRestClient(ExternalApiDocsProperties properties) {
        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    if (StringUtils.hasText(properties.bearerToken())) {
                        request.getHeaders().setBearerAuth(properties.bearerToken());
                    } else if (StringUtils.hasText(properties.username()) && StringUtils.hasText(properties.password())) {
                        String token = Base64.getEncoder()
                                .encodeToString((properties.username() + ":" + properties.password()).getBytes(StandardCharsets.UTF_8));
                        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Basic " + token);
                    }
                    return execution.execute(request, body);
                })
                .build();
    }
}
