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
public class CommentDto {

    @NotEmpty(message = "comment should not be empty")
    private String comment;
    @NotEmpty(message = "restaurant_id should not be empty")
    private Long reviewId;
    @NotEmpty(message = "userId should not be empty")
    private Long userId;
}
