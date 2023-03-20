package com.restaurant.finder.service;

import com.restaurant.finder.model.Restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */

@Service
public class RestaurantServiceImplement implements RestaurantService {

	@Value("${external.google.api.url}")
	private String baseUrl;

	@Value("${external.google.api.place.text.search}")
	private String urlPathUsingTextSearch;

	@Value("${external.google.api.photo}")
	private String urlGetPhotoPath;

	@Value("${external.google.api.key}")
	private String googleApiKey;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Restaurant getAllNearByRestaurants(String lat, String lng, String query, String radius, String pageToken) {
		UriComponents build = null;
		if (lat != null & lng != null & query != null & radius != null && pageToken == null) {
			build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl).path(urlPathUsingTextSearch)
					.queryParam("location", Float.parseFloat(lat) + "," + Float.parseFloat(lng))
					.queryParam("query", query).queryParam("radius", Long.parseLong(radius))
					.queryParam("key", googleApiKey).build();
			System.out.println("URL : " + build.toUriString());
		} else if (pageToken != null) {
			build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl).path(urlPathUsingTextSearch)
					.queryParam("pagetoken", pageToken).queryParam("key", googleApiKey).build();
			System.out.println("URL : " + build.toUriString());
		} else {
			throw new NullPointerException("Parameters cannot be none");
		}

		return restTemplate.getForObject(build.toUriString(), Restaurant.class);
	}

	@Override
	public void getRestaurantByPlaceId(String placeId) {
	}


}
