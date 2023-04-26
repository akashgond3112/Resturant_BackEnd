package com.restaurant.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    @NotEmpty(message = "userId should not be empty")
    private Long userId;
    @NotEmpty(message = "restaurant_id should not be empty")
    private int restaurantId;
    @NotEmpty(message = "rating should not be empty")
    private Integer rating;
    @NotEmpty(message = "review should not be empty")
    private String review;
    private Integer likes;
    @NotEmpty(message = "deliveryAvailable should not be empty")
    private String deliveryAvailable;
    @NotEmpty(message = "dineInAvailable should not be empty")
    private String dineInAvailable;

}
