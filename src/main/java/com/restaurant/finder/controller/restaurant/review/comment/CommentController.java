package com.restaurant.finder.controller.restaurant.review.comment;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.CommentDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.repository.UserRepository;
import com.restaurant.finder.responses.comment.CommentResponse;
import com.restaurant.finder.service.restaurant.review.comment.CommentService;
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
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewRepository restaurantReviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserRepository userRepository;


    /**
     * This class handles the creation of a comment for a given review.
     * The endpoint "/restaurant/reviews/{reviewId}/comments" is accessed via a POST request.
     * If the user's token has expired, a 401 Unauthorized response is returned.
     * If the user or the review specified by the reviewId cannot be found, a 400 Bad Request response is returned.
     * The comment is saved using the commentService, and the response contains the saved comment and a 201 Created status code.
     *
     * @param request    The HTTP servlet request containing the user's token
     * @param reviewId   The ID of the review to which the comment will be added
     * @param commentDto The CommentDto object containing the content of the comment
     * @return A ResponseEntity with either the saved comment and a 201 Created status code or an error message and an appropriate status code.
     * @throws InputMismatchException if the request was incorrect
     * @throws TokeExpiredException   if the token expired
     **/
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

    /**
     * Controller to handle HTTP requests related to updating a comment for a restaurant review.
     *
     * @param request    The HTTP servlet request object.
     * @param id         The ID of the comment to be updated.
     * @param commentDto The DTO object containing the updated comment details.
     * @return A ResponseEntity object containing the updated comment and a HTTP status code.
     * @throws InputMismatchException if the request was incorrect
     * @throws TokeExpiredException   if the token expired
     */
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

    /**
     * Deletes an existing review comment for a given review ID and user.
     *
     * @param request expect the HTTP Request
     * @param id      expect the comment id to be deleted
     * @return a status code if its success
     * @throws TokeExpiredException if the token expired
     * @throws NullPointerException if we didn't find any matching review for deleting
     */
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

    /**
     * Handles HTTP GET requests for retrieving a list of ReviewCommentResponse objects for a specific review id and user id.
     *
     * @param userId   the id of the user whose likes are to be retrieved, passed as a request parameter
     * @param reviewId the id of the review whose likes are to be retrieved, passed as a path variable
     * @return ResponseEntity object containing a list of CommentResponse objects and a HTTP status code indicating the success or failure of the request
     */
    @GetMapping("/restaurant/reviews/comments/{reviewId}")
    public ResponseEntity<?> findAllCommentsByReviewId(HttpServletRequest request, @RequestParam(name = "userId", required = false) Long userId, @PathVariable Long reviewId) {

        if (reviewId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (userId == null || userId == 0) {
            List<CommentResponse> commentResponses = commentService.findAllCommentByReviewId(reviewId);
            return new ResponseEntity<>(commentResponses, HttpStatus.OK);
        } else {
            try {
                ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
                if (objectResponseEntity != null) { // check user is authenticated or not or token is expired
                    throw new RuntimeException("Token was expired! or was empty in header!");
                }
                Optional<User> user = userRepository.findById(userId);
                if (user.get() == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                List<CommentResponse> commentResponses = commentService.findAllCommentByReviewId(reviewId, user.get());
                return new ResponseEntity<>(commentResponses, HttpStatus.OK);
            } catch (Exception exception) {
                System.out.println("Got an exception , either user was not valid or token was expired, so we will send the default review comment list with no permission to update or delete.");
                List<CommentResponse> commentResponses = commentService.findAllCommentByReviewId(reviewId);
                return new ResponseEntity<>(commentResponses, HttpStatus.OK);
            }
        }
    }

}

