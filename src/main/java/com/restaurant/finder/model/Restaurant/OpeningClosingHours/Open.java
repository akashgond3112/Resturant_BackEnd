package com.restaurant.finder.model.Restaurant.OpeningClosingHours;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */
public class Open {

	@JsonProperty(value = "date" , required = false)
	private String date;
	private Integer day;
	private String time;
	@JsonProperty(value = "truncated" , required = false)
	private Boolean truncated;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getTruncated() {
		return truncated;
	}
	public void setTruncated(Boolean truncated) {
		this.truncated = truncated;
	}
}
