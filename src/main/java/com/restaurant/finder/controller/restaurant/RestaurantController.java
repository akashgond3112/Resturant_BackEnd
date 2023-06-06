package com.restaurant.finder.controller.restaurant;

import com.restaurant.finder.pojo.Restaurant.Restaurant;
import com.restaurant.finder.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */

@RestController
@RequestMapping("/api/v1")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	/**
	 * @param lng except a parameter longitude
	 * @param lat except a parameter  latitude
	 * @param query except a parameter  What are you trying to search e.g. restaurant
	 * @param radius except a parameter radius
	 * @param pageToken except a parameter to get next page result
	 * @return Restaurant object
	 */
	@GetMapping("/restaurants")
	public Restaurant getAllNearByRestaurants(@RequestParam(name = "lat", required = false) String lat,
											  @RequestParam(name = "lng", required = false) String lng,
											  @RequestParam(name = "query", required = false) String query,
											  @RequestParam(name = "radius", required = false) String radius,
											  @RequestParam(name = "pageToken", required = false) String pageToken) {

		return restaurantService.getAllNearByRestaurants(lat, lng, query, radius, pageToken);

	}

	/**
	 * @param lat except a parameter longitude
	 * @param lng except a parameter  latitude
	 * @param keyword Which restaurant name you want to search
	 * @param radius except a parameter under the radius of
	 * @return the restaurant objects array
	 */
	@GetMapping("/restaurants/search/autoSuggest")
	public Restaurant getAutoSuggestedRestaurants(@RequestParam(name = "lat", required = true) String lat,
												  @RequestParam(name = "lng", required = true) String lng,
												  @RequestParam(name = "keyword", required = true) String keyword,
												  @RequestParam(name = "radius", required = true) String radius) {

		return restaurantService.getAutoSuggestedRestaurants(lat, lng, keyword, radius);

	}

	/**
	 * @param placeId expect a placeId which is restaurantID to get all the details of restaurant
	 * @return the restaurant object
	 */
	@GetMapping("restaurant/{placeId}")
	public Restaurant getRestaurant(@PathVariable("placeId") String placeId) {
		return restaurantService.getRestaurantByPlaceId(placeId);
	}


}
