package com.restaurant.finder.model.Restaurant.Utilities;

import com.restaurant.finder.model.Restaurant.Location.Northeast;
import com.restaurant.finder.model.Restaurant.Location.Southwest;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
public class Viewport {
	private Northeast northeast;
	private Southwest southwest;
	public Northeast getNortheast() {
		return northeast;
	}
	public void setNortheast(Northeast northeast) {
		this.northeast = northeast;
	}
	public Southwest getSouthwest() {
		return southwest;
	}
	public void setSouthwest(Southwest southwest) {
		this.southwest = southwest;
	}
}
