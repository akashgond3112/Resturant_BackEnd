package com.restaurant.finder.responses.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 25042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewLikeResponse {

    private int restaurantId;
    private Long reviewId;
    private Long reviewLikeId;
    private Long userId;
    private String userName;
    private boolean canRemoveLike;
}
