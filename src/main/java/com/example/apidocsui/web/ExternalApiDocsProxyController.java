package com.example.apidocsui.web;

import com.example.apidocsui.config.ExternalApiDocsProperties;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class ExternalApiDocsProxyController {

    private final RestClient restClient;
    private final ExternalApiDocsProperties properties;

    public ExternalApiDocsProxyController(RestClient externalApiDocsRestClient, ExternalApiDocsProperties properties) {
        this.restClient = externalApiDocsRestClient;
        this.properties = properties;
    }

    @GetMapping(value = "/api-docs/{group}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String apiDocs(@PathVariable String group) {
        if (!StringUtils.hasText(properties.baseUrl())) {
            throw new ResponseStatusException(BAD_REQUEST, "Property external.api-docs.base-url must be configured");
        }

        String normalizedBaseUrl = properties.baseUrl().endsWith("/")
                ? properties.baseUrl().substring(0, properties.baseUrl().length() - 1)
                : properties.baseUrl();

        return restClient.get()
                .uri(normalizedBaseUrl + "/" + group)
                .retrieve()
                .body(String.class);
    }
}
