package com.restaurant.finder.controller;

import com.restaurant.finder.model.Restaurant.Restaurant;
import com.restaurant.finder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping("/restaurants")
	public Restaurant getAllNearByRestaurants(@RequestParam(name = "lat", required = false) String lat,
			@RequestParam(name = "lng", required = false) String lng,
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "radius", required = false) String radius,
			@RequestParam(name = "pageToken", required = false) String pageToken) {

		return restaurantService.getAllNearByRestaurants(lat, lng, query, radius, pageToken);

	}

	@GetMapping("restaurants/{placeId}")
	public void getRestaurant(@PathVariable("placeId") String placeId) {

	}


}
