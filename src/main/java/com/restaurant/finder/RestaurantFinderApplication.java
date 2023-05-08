package com.restaurant.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestaurantFinderApplication {

	private final String theAllowedOrigins = "http://localhost:3000/";

	public static void main(String[] args) {
		SpringApplication.run(RestaurantFinderApplication.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer(){

		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry.addMapping("/**")
						.allowedOrigins(theAllowedOrigins);
			}
		};
	}

}
