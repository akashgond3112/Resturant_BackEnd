package com.restaurant.finder.pojo.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 14march2023 Copyright (C) 2023 Newcastle University, UK
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value()
public class Restaurant {
	@JsonProperty("html_attributions")
	List<String> htmlAttributions = new ArrayList<String>();

	@JsonProperty(value = "next_page_token" , required = false)
	String nextPageToken;

	@JsonProperty(value = "results" , required = false)
	List<Result> results = new ArrayList<Result>();
	@JsonProperty(value = "result" , required = false)
	Result result = new Result();
	String status;

}
