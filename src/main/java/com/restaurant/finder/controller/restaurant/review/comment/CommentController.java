package com.restaurant.finder.controller.restaurant.review.comment;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.CommentDto;
import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.responses.review.CommentResponse;
import com.restaurant.finder.responses.review.ReviewResponse;
import com.restaurant.finder.service.restaurant.review.ReviewService;
import com.restaurant.finder.service.restaurant.review.comment.CommentService;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    private ReviewRepository restaurantReviewRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @PostMapping("/restaurant/reviews/{reviewId}/comments")
    public ResponseEntity<?> createComment(HttpServletRequest request, @PathVariable Long reviewId, @RequestBody CommentDto commentDto) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        Optional<Review> review = restaurantReviewRepository.findById(reviewId);
        if (user != null && review.get() != null) {
            commentDto.setUserId(user.getId());
            commentDto.setReviewId(review.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find either user associated or review Id is null || 0.");
        }

        return new ResponseEntity<>(commentService.saveComment(user, review.get(), commentDto), HttpStatus.CREATED);
    }

    @PutMapping("/restaurant/reviews/comments/{id}")
    public ResponseEntity<?> updateComment(HttpServletRequest request, @PathVariable Long id, @RequestBody CommentDto commentDto) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        Optional<Comment> comment = commentRepository.findById(id);

        if (user == null || id == 0 || comment.get() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find either user associated or review Id is null || 0.");
        } else {
            try {
                commentDto.setUserId(user.getId());
                commentDto.setReviewId(comment.get().getReview().getId());
                return new ResponseEntity<>(commentService.updateComment(id, commentDto, user, comment.get().getReview()), HttpStatus.OK);
            } catch (InvalidRequestException invalidRequestException) {
                return new ResponseEntity<String>(invalidRequestException.getMessage(), null, HttpStatus.FORBIDDEN);
            } catch (NullPointerException nullPointerException) {
                return new ResponseEntity<String>(nullPointerException.getMessage(), null, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @DeleteMapping("/restaurant/reviews/comments/{id}")
    public ResponseEntity<?> deleteComment(HttpServletRequest request, @PathVariable Long id) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                commentService.deleteCommentById(id, user);
            } catch (InvalidRequestException invalidRequestException) {
                return new ResponseEntity<String>(invalidRequestException.getMessage(), null, HttpStatus.FORBIDDEN);
            } catch (NullPointerException nullPointerException) {
                return new ResponseEntity<String>(nullPointerException.getMessage(), null, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/restaurant/reviews/comments/{id}")
    public ResponseEntity<List<CommentResponse>> findAllCommentsByReviewId(HttpServletRequest request, @PathVariable Long id) {

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<CommentResponse> reviewList = commentService.findAllCommentByReviewId(id, user);
            return new ResponseEntity<>(reviewList, HttpStatus.OK);
        }
    }

}

