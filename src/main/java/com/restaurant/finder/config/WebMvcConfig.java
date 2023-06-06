package com.restaurant.finder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 06062023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${external.react.frontend.app.url}")
    private String corsUrl;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(corsUrl)
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(-1);
    }
}
