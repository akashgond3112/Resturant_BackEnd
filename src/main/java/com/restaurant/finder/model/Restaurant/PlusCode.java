package com.restaurant.finder.model.Restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
public class PlusCode {
	@JsonProperty("compound_code")
	private String compoundCode;
	@JsonProperty("global_code")
	private String globalCode;

	public String getCompoundCode() {
		return compoundCode;
	}

	public void setCompoundCode(String compoundCode) {
		this.compoundCode = compoundCode;
	}

	public String getGlobalCode() {
		return globalCode;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}
}
