package com.restaurant.finder.service.restaurant.review.like;

import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;

import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 26042023
 * Copyright (C) 2023 Newcastle University, UK
 */
public interface LikeService {

    ReviewLikeResponse saveReviewLike(User user, Review review);

    void deleteReviewLikeById(Long likeId, User user);

    List<ReviewLikeResponse> findAllReviewLikeByReviewId(Long reviewId, User user);
}
