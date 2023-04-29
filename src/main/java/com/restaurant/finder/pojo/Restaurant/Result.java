package com.restaurant.finder.pojo.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.restaurant.finder.pojo.Restaurant.Location.Geometry;
import com.restaurant.finder.pojo.Restaurant.OpeningClosingHours.CurrentOpeningHours;
import com.restaurant.finder.pojo.Restaurant.OpeningClosingHours.OpeningHours;
import com.restaurant.finder.pojo.Restaurant.Photo.Photo;
import com.restaurant.finder.pojo.Restaurant.Review.Review;
import com.restaurant.finder.pojo.Restaurant.Utilities.PlusCode;
import com.restaurant.finder.pojo.Restaurant.address.AddressComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@JsonProperty(value = "address_components", required = false)
	private List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
	@JsonProperty(value = "adr_address", required = false)
	private String adrAddress;
	@JsonProperty("business_status")
	private String businessStatus;
	@JsonProperty(value = "current_opening_hours", required = false)
	private CurrentOpeningHours currentOpeningHours;
	@JsonProperty(required = false)
	private Boolean delivery;
	@JsonProperty(value = "dine_in", required = false)
	private Boolean dineIn;
	@JsonProperty(value = "formatted_address")
	private String formattedAddress;
	@JsonProperty(value = "formatted_phone_number", required = false)
	private String formattedPhoneNumber;
	private Geometry geometry;
	private String icon;
	@JsonProperty(value = "icon_background_color")
	private String iconBackgroundColor;
	@JsonProperty(value = "icon_mask_base_uri")
	private String iconMaskBaseUri;
	@JsonProperty(value = "international_phone_number", required = false)
	private String internationalPhoneNumber;
	private String name;
	@JsonProperty(value = "opening_hours")
	private OpeningHours openingHours;
	private List<Photo> photos = new ArrayList<Photo>();
	@JsonProperty(value = "place_id")
	private String placeId;
	@JsonProperty(value = "plus_code")
	private PlusCode plusCode;
	@JsonProperty(value = "price_level", required = false)
	private Integer priceLevel;
	private Double rating;
	private String reference;
	@JsonProperty(required = false)
	private Boolean reservable;
	@JsonProperty(required = false)
	private List<Review> reviews = new ArrayList<Review>();
	@JsonProperty(value = "serves_beer", required = false)
	private Boolean servesBeer;
	@JsonProperty(value = "serves_dinner", required = false)
	private Boolean servesDinner;
	@JsonProperty(value = "serves_wine", required = false)
	private Boolean servesWine;
	@JsonProperty(required = false)
	private Boolean takeout;
	private List<String> types = new ArrayList<String>();
	@JsonProperty(required = false)
	private String url;
	@JsonProperty(value = "user_ratings_total", required = false)
	private Integer userRatingsTotal;
	@JsonProperty(value = "utc_offset", required = false)
	private Integer utcOffset;
	@JsonProperty(value = "vicinity", required = false)
	private String vicinity;

	public List<AddressComponent> getAddressComponents() {
		return addressComponents;
	}

	public void setAddressComponents(List<AddressComponent> addressComponents) {
		this.addressComponents = addressComponents;
	}

	public String getAdrAddress() {
		return adrAddress;
	}

	public void setAdrAddress(String adrAddress) {
		this.adrAddress = adrAddress;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public CurrentOpeningHours getCurrentOpeningHours() {
		return currentOpeningHours;
	}

	public void setCurrentOpeningHours(CurrentOpeningHours currentOpeningHours) {
		this.currentOpeningHours = currentOpeningHours;
	}

	public Boolean getDelivery() {
		return delivery;
	}

	public void setDelivery(Boolean delivery) {
		this.delivery = delivery;
	}

	public Boolean getDineIn() {
		return dineIn;
	}

	public void setDineIn(Boolean dineIn) {
		this.dineIn = dineIn;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}

	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
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

	public String getInternationalPhoneNumber() {
		return internationalPhoneNumber;
	}

	public void setInternationalPhoneNumber(String internationalPhoneNumber) {
		this.internationalPhoneNumber = internationalPhoneNumber;
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

	public Boolean getReservable() {
		return reservable;
	}

	public void setReservable(Boolean reservable) {
		this.reservable = reservable;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Boolean getServesBeer() {
		return servesBeer;
	}

	public void setServesBeer(Boolean servesBeer) {
		this.servesBeer = servesBeer;
	}

	public Boolean getServesDinner() {
		return servesDinner;
	}

	public void setServesDinner(Boolean servesDinner) {
		this.servesDinner = servesDinner;
	}

	public Boolean getServesWine() {
		return servesWine;
	}

	public void setServesWine(Boolean servesWine) {
		this.servesWine = servesWine;
	}

	public Boolean getTakeout() {
		return takeout;
	}

	public void setTakeout(Boolean takeout) {
		this.takeout = takeout;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUserRatingsTotal() {
		return userRatingsTotal;
	}

	public void setUserRatingsTotal(Integer userRatingsTotal) {
		this.userRatingsTotal = userRatingsTotal;
	}

	public Integer getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(Integer utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
}
