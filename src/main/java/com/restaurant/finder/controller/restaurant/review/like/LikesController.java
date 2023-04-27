package com.restaurant.finder.controller.restaurant.review.like;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.ReviewLikeDto;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.repository.UserRepository;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;
import com.restaurant.finder.service.restaurant.review.like.LikeService;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
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
public class LikesController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository restaurantReviewRepository;

    /**
     * Creates a new review like for a given review and user.
     *
     * @param request       the HTTP servlet request object
     * @param reviewLikeDto a ReviewLikeDto object containing the review and user IDs
     * @return a ResponseEntity object with the ReviewLikeResponse object and HTTP status code
     * @throws NoSuchElementException if we cannot find review or user
     * @throws InputMismatchException if the request was incorrect
     * @throws TokeExpiredException   if the token expired
     */
    @PostMapping("/restaurant/reviews/likes")
    public ResponseEntity<?> createReviewLike(HttpServletRequest request, @RequestBody ReviewLikeDto reviewLikeDto) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        } else if (reviewLikeDto.getReviewId() == null || reviewLikeDto.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        Optional<Review> review = restaurantReviewRepository.findById(reviewLikeDto.getReviewId());

        try {
            if (user != null || review.get() != null) {
                return new ResponseEntity<>(likeService.saveReviewLike(user, review.get()), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<String>(noSuchElementException.getMessage(), null, HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * Deletes an existing review like for a given like ID and user.
     * @param request the HTTP servlet request object
     * @param id      the ID of the review like to be deleted
     * @return a ResponseEntity object with the HTTP status code
     * @throws TokeExpiredException if the token expired
     * @throws NullPointerException if we didn't find any matching review for deleting
     */
    @DeleteMapping("/restaurant/reviews/likes/{id}")
    public ResponseEntity<?> deleteReviewLike(HttpServletRequest request, @PathVariable Long id) {
        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                likeService.deleteReviewLikeById(id, user);
            } catch (InvalidRequestException invalidRequestException) {
                return new ResponseEntity<String>(invalidRequestException.getMessage(), null, HttpStatus.FORBIDDEN);
            } catch (NullPointerException nullPointerException) {
                return new ResponseEntity<String>(nullPointerException.getMessage(), null, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * Handles HTTP GET requests for retrieving a list of ReviewLikeResponse objects for a specific review id and user id.
     * @param userId   the id of the user whose likes are to be retrieved, passed as a request parameter
     * @param reviewId the id of the review whose likes are to be retrieved, passed as a path variable
     * @return ResponseEntity object containing a list of ReviewLikeResponse objects and a HTTP status code indicating the success or failure of the request
     */
    @GetMapping("/restaurant/reviews/{reviewId}/likes")
    public ResponseEntity<List<ReviewLikeResponse>> findLikesByReviewId(@RequestParam Long userId, @PathVariable Long reviewId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.get() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<ReviewLikeResponse> reviewLikeResponses = likeService.findAllReviewLikeByReviewId(reviewId, user.get());
            return new ResponseEntity<>(reviewLikeResponses, HttpStatus.OK);
        }

    }
}

