package com.restaurant.finder.model.Restaurant.Review;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */
public class Review {
	private String authorName;
	private String authorUrl;
	private String language;
	private String originalLanguage;
	private String profilePhotoUrl;
	private Integer rating;
	private String relativeTimeDescription;
	private String text;
	private Integer time;
	private Boolean translated;
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOriginalLanguage() {
		return originalLanguage;
	}
	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}
	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}
	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getRelativeTimeDescription() {
		return relativeTimeDescription;
	}
	public void setRelativeTimeDescription(String relativeTimeDescription) {
		this.relativeTimeDescription = relativeTimeDescription;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Boolean getTranslated() {
		return translated;
	}
	public void setTranslated(Boolean translated) {
		this.translated = translated;
	}
}
