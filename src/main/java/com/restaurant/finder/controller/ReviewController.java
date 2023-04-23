package com.restaurant.finder.controller;

import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.service.restaurant.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurant/reviews")
    public ResponseEntity<Review> create(@RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.saveReview(reviewDto), HttpStatus.CREATED);
    }

    @PutMapping("/restaurant/reviews/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.updateReview(id, reviewDto), HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/reviews/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long reviewId) {
        reviewService.deleteById(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/restaurant/reviews/{restaurantId}")
    public ResponseEntity<List<Review>> findByRestaurantId(@PathVariable Long restaurantId) {
        return new ResponseEntity<>(reviewService.findAllReviewByRestaurantId(restaurantId), HttpStatus.OK);
    }

    @PostMapping("/restaurant/reviews/{reviewId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long reviewId, @RequestBody Comment comment) {
        return new ResponseEntity<>(reviewService.saveComment(reviewId, comment), HttpStatus.CREATED);
    }

    @PutMapping("/restaurant/reviews/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return new ResponseEntity<>(reviewService.updateComment(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/reviews/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        reviewService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*Review Likes*/
    @PostMapping("/restaurant/reviews/{reviewId}/likes")
    public ResponseEntity<ReviewLike> createReviewLike(@PathVariable Long reviewId, @RequestBody ReviewLike reviewLike) {
        return new ResponseEntity<>(reviewService.saveReviewLike(reviewId, reviewLike), HttpStatus.CREATED);
    }

    @DeleteMapping("/restaurant/reviews/likes/{id}")
    public ResponseEntity<HttpStatus> deleteReviewLike(@PathVariable Long id) {
        reviewService.deleteReviewLikeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/restaurant/reviews/{reviewId}/likes")
    public ResponseEntity<List<ReviewLike>> findLikesByReviewId(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.findAllLikesByReviewId(reviewId), HttpStatus.OK);
    }
}

