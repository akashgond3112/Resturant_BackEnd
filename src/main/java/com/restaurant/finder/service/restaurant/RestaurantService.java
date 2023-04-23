package com.restaurant.finder.service.restaurant;

import com.restaurant.finder.model.Restaurant.Restaurant;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
public interface RestaurantService {

	public Restaurant getAllNearByRestaurants(String lat, String lng, String query, String radius, String pageToken);

	public Restaurant getAutoSuggestedRestaurants(String lat, String lng, String keyword, String radius);

	public Restaurant getRestaurantByPlaceId(String placeId);

}
