package com.restaurant.finder.model.Restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
public class OpeningHours {

	@JsonProperty("open_now")
	private Boolean openNow;

	public Boolean getOpenNow() {
		return openNow;
	}

	public void setOpenNow(Boolean openNow) {
		this.openNow = openNow;
	}
}