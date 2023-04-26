package com.restaurant.finder.controller.restaurant.review;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.repository.UserRepository;
import com.restaurant.finder.responses.review.ReviewResponse;
import com.restaurant.finder.service.restaurant.review.ReviewService;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

/**
 * @author Team-alpha
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

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/restaurant/reviews")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody ReviewDto reviewDto) {


        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user != null) {
            reviewDto.setUserId(user.getId());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reviewService.saveReview(user, reviewDto), HttpStatus.CREATED);
    }

    @PutMapping("/restaurant/reviews/{reviewId}")
    public ResponseEntity<?> update(HttpServletRequest request, @PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {


        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(reviewService.updateReview(reviewId, reviewDto, user), HttpStatus.OK);
            } catch (InvalidRequestException invalidRequestException) {
                return new ResponseEntity<String>(invalidRequestException.getMessage(), null, HttpStatus.FORBIDDEN);
            } catch (NullPointerException nullPointerException) {
                return new ResponseEntity<String>(nullPointerException.getMessage(), null, HttpStatus.BAD_REQUEST);
            }
        }

    }

    @DeleteMapping("/restaurant/reviews/{reviewId}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long reviewId) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                reviewService.deleteById(reviewId, user);
            } catch (InvalidRequestException invalidRequestException) {
                return new ResponseEntity<String>(invalidRequestException.getMessage(), null, HttpStatus.FORBIDDEN);
            } catch (NullPointerException nullPointerException) {
                return new ResponseEntity<String>(nullPointerException.getMessage(), null, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/restaurant/reviews/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> findAllReviewsByRestaurantId(@RequestParam Long userId, @PathVariable Long restaurantId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.get() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<ReviewResponse> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId, user.get());
            return new ResponseEntity<>(reviewList, HttpStatus.OK);
        }
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

