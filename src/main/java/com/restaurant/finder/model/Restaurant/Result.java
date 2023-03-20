package com.restaurant.finder.model.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@JsonProperty("business_status")
	private String businessStatus;

	@JsonProperty("formatted_address")
	private String formattedAddress;

	private Geometry geometry;

	private String icon;

	@JsonProperty("icon_background_color")
	private String iconBackgroundColor;

	@JsonProperty("icon_mask_base_uri")
	private String iconMaskBaseUri;

	private String name;

	@JsonProperty("opening_hours")
	private OpeningHours openingHours;

	private List<Photo> photos = new ArrayList<Photo>();

	@JsonProperty("place_id")
	private String placeId;

	@JsonProperty("plus_code")
	private PlusCode plusCode;

	@JsonProperty("price_level")
	private Integer priceLevel;
	private Double rating;
	private String reference;
	private List<String> types = new ArrayList<String>();

	@JsonProperty("user_ratings_total")
	private Integer userRatingsTotal;

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconBackgroundColor() {
		return iconBackgroundColor;
	}

	public void setIconBackgroundColor(String iconBackgroundColor) {
		this.iconBackgroundColor = iconBackgroundColor;
	}

	public String getIconMaskBaseUri() {
		return iconMaskBaseUri;
	}

	public void setIconMaskBaseUri(String iconMaskBaseUri) {
		this.iconMaskBaseUri = iconMaskBaseUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OpeningHours getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public PlusCode getPlusCode() {
		return plusCode;
	}

	public void setPlusCode(PlusCode plusCode) {
		this.plusCode = plusCode;
	}

	public Integer getPriceLevel() {
		return priceLevel;
	}

	public void setPriceLevel(Integer priceLevel) {
		this.priceLevel = priceLevel;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Integer getUserRatingsTotal() {
		return userRatingsTotal;
	}

	public void setUserRatingsTotal(Integer userRatingsTotal) {
		this.userRatingsTotal = userRatingsTotal;
	}
}
