package com.restaurant.finder.model.Restaurant.OpeningClosingHours;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */
public class CurrentOpeningHours {

	private Boolean openNow;
	private List<Period> periods = new ArrayList<Period>();
	private List<String> weekdayText = new ArrayList<String>();
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
