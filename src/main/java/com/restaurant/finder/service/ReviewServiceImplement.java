package com.restaurant.finder.service;

import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.repository.ReviewLikeRepository;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Service
public class ReviewServiceImplement implements ReviewService {

    @Autowired
    private ReviewRepository restaurantReviewRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Review saveReview(Long userId, Review restaurantReview) {

        return userRepository.findById(userId).map(user -> {
            restaurantReview.setUser(user);
            return restaurantReviewRepository.save(restaurantReview);
        }).orElseThrow(() -> new ResourceNotFoundException("Not User found with id = " + restaurantReview.getUser().getId()));

    }

    @Override
    public Review updateReview(Long id, Review restaurantReview) {

        Review review = restaurantReviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + id));

        // Check if all fields in the restaurantReview object are null
        boolean allFieldsNull = restaurantReview.getLikes() == null &&
                restaurantReview.getRating() == null &&
                restaurantReview.getReview() == null;

        if (allFieldsNull) {
            throw new InvalidRequestException("At least one field in the review object must be non-null");
        }

        if (restaurantReview.getLikes() != null) {
            review.setLikes(restaurantReview.getLikes());
        }
        if (restaurantReview.getRating() != null) {
            review.setRating(restaurantReview.getRating());
        }
        if (restaurantReview.getReview() != null) {
            review.setReview(restaurantReview.getReview());
        }

        restaurantReviewRepository.save(review);
        return review;
    }

    @Override
    public void deleteById(Long id) {
        restaurantReviewRepository.deleteById(id);
    }

    @Override
    public List<Review> findAllReviewByRestaurantId(Long restaurant_id) {
        return restaurantReviewRepository.findReviewsByRestaurant_id(restaurant_id);
    }

    @Override
    public Comment saveComment(Long reviewId, Comment commentRequest) {

        return restaurantReviewRepository.findById(reviewId).map(review -> {
            commentRequest.setReview(review);
            commentRequest.setUser(review.getUser());
            return commentRepository.save(commentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + reviewId));
    }

    @Override
    public Comment updateComment(Long commentId, Comment commentRequest) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
        comment.setText(commentRequest.getText());
        return comment;
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findAllCommentByReviewId(Long reviewId) {
        return commentRepository.findAllByReviewId(reviewId);
    }

    /*Review Likes*/
    @Override
    public ReviewLike saveReviewLike(Long reviewId, ReviewLike reviewLikeRequest) {

        return restaurantReviewRepository.findById(reviewId).map(review -> {
            reviewLikeRequest.setReview(review);
            reviewLikeRequest.setUser(review.getUser());
            return reviewLikeRepository.save(reviewLikeRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + reviewId));

    }

    @Override
    public void deleteReviewLikeById(Long id) {
        reviewLikeRepository.deleteById(id);
    }

    @Override
    public List<ReviewLike> findAllLikesByReviewId(Long reviewId) {
        return reviewLikeRepository.findAllByReviewId(reviewId);
    }
}

