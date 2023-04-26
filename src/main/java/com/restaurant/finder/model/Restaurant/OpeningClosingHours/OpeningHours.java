package com.restaurant.finder.model.Restaurant.OpeningClosingHours;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */
public class OpeningHours {

	@JsonProperty("open_now")
	Boolean openNow;
	@JsonProperty(value = "periods" , required = false)
	List<Period> periods = new ArrayList<Period>();
	@JsonProperty(value = "weekday_text" , required = false)
	List<String> weekdayText = new ArrayList<String>();
	public Boolean getOpenNow() {
		return openNow;
	}
	public void setOpenNow(Boolean openNow) {
		this.openNow = openNow;
	}
	public List<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}
	public List<String> getWeekdayText() {
		return weekdayText;
	}
	public void setWeekdayText(List<String> weekdayText) {
		this.weekdayText = weekdayText;
	}
}
