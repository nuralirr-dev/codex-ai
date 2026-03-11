package com.example.apidocsui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApiDocsUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDocsUiApplication.class, args);
    }
}
