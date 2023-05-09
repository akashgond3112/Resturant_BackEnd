package com.restaurant.finder.controller.restaurant.review;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.ReviewDto;
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

    /**
     * @param request   expect the HTTP Request
     * @param reviewDto an reviewDto object
     * @return the Review response
     * @throws InputMismatchException if the request was incorrect
     * @throws TokeExpiredException   if the token expired
     */
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

    /**
     * @param request   expect the HTTP Request
     * @param reviewId  expect the review id to be updated
     * @param reviewDto an review object
     * @return the Review response
     * @throws InputMismatchException if the request was incorrect
     * @throws TokeExpiredException   if the token expired
     */
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

    /**
     * @param request  expect the HTTP Request
     * @param reviewId expect the review id to be deleted
     * @return a status code if its success
     * @throws TokeExpiredException if the token expired
     * @throws NullPointerException if we didn't find any matching review for deleting
     */
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

    /**
     * @param userId       expect an userId
     * @param restaurantId expect an restaurantId
     * @return List<ReviewResponse></ReviewResponse>
     */
    @GetMapping("/restaurant/reviews/{restaurantId}")
    public ResponseEntity<?> findAllReviewsByRestaurantId(HttpServletRequest request, @RequestParam(name = "userId", required = false) Long userId, @PathVariable String restaurantId) {

        if (restaurantId == null || restaurantId.isEmpty() || restaurantId.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (userId == null || userId == 0) {
            List<ReviewResponse> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId);
            return new ResponseEntity<>(reviewList, HttpStatus.OK);
        } else {
            try {
                ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
                if (objectResponseEntity != null) { // check user is authenticated or not or token is expired
                    throw new RuntimeException("Token was expired! or was empty in header!");
                }
                Optional<User> user = userRepository.findById(userId);
                if (user.get() == null) { // check if we find the matching user details
                    throw new RuntimeException("Cannot find the matching user!!");
                }
                List<ReviewResponse> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId, user.get());
                return new ResponseEntity<>(reviewList, HttpStatus.OK);
            } catch (Exception exception) {
                System.out.println("Got an exception , either user was not valid or token was expired, so we will send the default review list with no permission to update or delete.");
                List<ReviewResponse> reviewList = reviewService.findAllReviewByRestaurantId(restaurantId); // send the default review list with not access to edit access for the review provided by the requested user
                return new ResponseEntity<>(reviewList, HttpStatus.PARTIAL_CONTENT);
            }

        }
    }
}

