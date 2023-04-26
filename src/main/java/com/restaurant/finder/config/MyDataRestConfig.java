package com.restaurant.finder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 12March2023 Copyright (C) 2023 Newcastle University, UK
 */

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	private final String theAllowedOrigins = "http://localhost:3000/";

	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration,
			CorsRegistry corsRegistry) {

		/* Configure CORS Mapping */
		corsRegistry.addMapping(repositoryRestConfiguration.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
	}
}
