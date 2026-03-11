package com.example.apidocsui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.api-docs")
public record ExternalApiDocsProperties(
        String baseUrl,
        String username,
        String password,
        String bearerToken
) {
}
