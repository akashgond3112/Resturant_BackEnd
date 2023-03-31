package com.restaurant.finder.model.Restaurant.address;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */

public class AddressComponent {

	private String longName;
	private String shortName;
	private List<String> types = new ArrayList<String>();
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
}
