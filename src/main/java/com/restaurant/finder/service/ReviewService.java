package com.restaurant.finder.service;

import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;

import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
public interface ReviewService {
    Review saveReview(Long userId, Review restaurantReview);

    Review updateReview(Long id, Review restaurantReview);

    void deleteById(Long id);

    List<Review> findAllReviewByRestaurantId(Long restaurantId);

    Comment saveComment(Long reviewId, Comment comment);

    Comment updateComment(Long commentId, Comment comment);

    void deleteCommentById(Long id);

    List<Comment> findAllCommentByReviewId(Long reviewId);

    ReviewLike saveReviewLike(Long reviewId, ReviewLike reviewLike);

    void deleteReviewLikeById(Long id);

    List<ReviewLike> findAllLikesByReviewId(Long reviewId);

}
