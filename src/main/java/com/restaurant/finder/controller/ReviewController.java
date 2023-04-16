package com.restaurant.finder.controller;

import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.service.ReviewService;
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
@RequestMapping("/api/v1/restaurant/reviews")
@CrossOrigin("http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<Review> create(@RequestBody Review reviewRequest, @RequestParam Long userId) {
        return new ResponseEntity<>(reviewService.saveReview(userId, reviewRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id, @RequestBody Review review) {
        return new ResponseEntity<>(reviewService.updateReview(id, review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<Review>> findByRestaurantId(@PathVariable Long restaurantId) {
        return new ResponseEntity<>(reviewService.findAllReviewByRestaurantId(restaurantId), HttpStatus.OK);
    }

    /*Comments*/
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long reviewId, @RequestBody Comment comment) {
        return new ResponseEntity<>(reviewService.saveComment(reviewId, comment), HttpStatus.CREATED);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return new ResponseEntity<>(reviewService.updateComment(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        reviewService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*Review Likes*/
    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<ReviewLike> createReviewLike(@PathVariable Long reviewId, @RequestBody ReviewLike reviewLike) {
        return new ResponseEntity<>(reviewService.saveReviewLike(reviewId, reviewLike), HttpStatus.CREATED);
    }

    @DeleteMapping("/likes/{id}")
    public ResponseEntity<HttpStatus> deleteReviewLike(@PathVariable Long id) {
        reviewService.deleteReviewLikeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{reviewId}/likes")
    public ResponseEntity<List<ReviewLike>> findLikesByReviewId(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.findAllLikesByReviewId(reviewId), HttpStatus.OK);
    }
}

