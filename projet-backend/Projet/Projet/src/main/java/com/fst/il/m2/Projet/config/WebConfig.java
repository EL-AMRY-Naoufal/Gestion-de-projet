package com.fst.il.m2.Projet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig /*implements WebMvcConfigurer*/ {
    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Match your API endpoints
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }*/
}