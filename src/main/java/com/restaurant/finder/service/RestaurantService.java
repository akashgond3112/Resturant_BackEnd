package com.restaurant.finder.service;

import org.springframework.http.HttpHeaders;

import com.restaurant.finder.model.Restaurant.Restaurant;
import com.restaurant.finder.model.Restaurant.Result;
import org.springframework.http.ResponseEntity;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
public interface RestaurantService {

	public Restaurant getAllNearByRestaurants(String lat, String lng, String query, String radius, String pageToken);

	public void getRestaurantByPlaceId(String placeId);

}
