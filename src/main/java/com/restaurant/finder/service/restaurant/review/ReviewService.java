package com.restaurant.finder.service.restaurant.review;

import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.responses.review.ReviewResponse;

import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
public interface ReviewService {
    ReviewResponse saveReview(User user, ReviewDto reviewDto);

    ReviewResponse updateReview(Long id, ReviewDto reviewDto, User user);

    void deleteById(Long reviewId, User user);

    List<ReviewResponse> findAllReviewByRestaurantId(Long restaurantId, User user);

}
