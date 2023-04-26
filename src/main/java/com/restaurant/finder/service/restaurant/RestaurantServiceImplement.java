package com.restaurant.finder.service.restaurant;

import com.restaurant.finder.pojo.Restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */

@Service
public class RestaurantServiceImplement implements RestaurantService {

	@Value("${external.google.api.url}")
	private String baseUrl;

	@Value("${external.google.api.place.text.search}")
	private String urlPathUsingTextSearch;

	@Value("${external.google.api.place.nearBy.search}")
	private String urlPathUsingNearBySearch;

	@Value("${external.google.api.photo}")
	private String urlGetPhotoPath;


	@Value("${external.google.api.place.details}")
	private String urlGetPlaceDetails;

	@Value("${external.google.api.key}")
	private String googleApiKey;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Restaurant getAllNearByRestaurants(String lat, String lng, String query, String radius, String pageToken) {
		UriComponents build = null;
		if (lat != null & lng != null & query != null & radius != null && pageToken.isEmpty()) {
			build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl).path(urlPathUsingTextSearch)
					.queryParam("location", Float.parseFloat(lat) + "," + Float.parseFloat(lng))
					.queryParam("query", query).queryParam("radius", Long.parseLong(radius))
					.queryParam("key", googleApiKey).build();
			System.out.println("URL : " + build.toUriString());
		} else if (!pageToken.isEmpty()) {
			build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl).path(urlPathUsingTextSearch)
					.queryParam("pagetoken", pageToken).queryParam("key", googleApiKey).build();
			System.out.println("URL : " + build.toUriString());
		} else {
			throw new NullPointerException("Parameters cannot be none");
		}

		return restTemplate.getForObject(build.toUriString(), Restaurant.class);
	}

	@Override
	public Restaurant getAutoSuggestedRestaurants(String lat, String lng, String keyword, String radius) {

		UriComponents build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl)
				.path(urlPathUsingNearBySearch)
				.queryParam("keyword", keyword)
				.queryParam("type", "restaurant")
				.queryParam("location", Float.parseFloat(lat) + "," + Float.parseFloat(lng))
				.queryParam("radius", Long.parseLong(radius)).queryParam("key", googleApiKey).build();

		System.out.println("URL : " + build.toUriString());
		return restTemplate.getForObject(build.toUriString(), Restaurant.class);
	}

	@Override
	public Restaurant getRestaurantByPlaceId(String placeId) {

		UriComponents build = UriComponentsBuilder.newInstance().scheme("https").host(baseUrl)
				.path(urlGetPlaceDetails)
				.queryParam("place_id", placeId)
				.queryParam("key", googleApiKey).build();

		System.out.println("URL : " + build.toUriString());
		return restTemplate.getForObject(build.toUriString(), Restaurant.class);
	}


}
