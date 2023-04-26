package com.restaurant.finder.pojo.Restaurant.OpeningClosingHours;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23032023 Copyright (C) 2023 Newcastle University, UK
 */
public class Period {

	private Close close;
	private Open open;
	public Close getClose() {
		return close;
	}
	public void setClose(Close close) {
		this.close = close;
	}
	public Open getOpen() {
		return open;
	}
	public void setOpen(Open open) {
		this.open = open;
	}
}
