package com.restaurant.finder.service.restaurant.review;

import com.restaurant.finder.dto.ReviewDto;
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

    /**
     * @param reviewDto except and object with all the mandatory review information
     * @return saved review object
     * @throws ResourceNotFoundException if user is not found
     */
    @Override
    public Review saveReview(ReviewDto reviewDto) {

        Review review = new Review();
        return userRepository.findById(Long.valueOf(reviewDto.getUserId())).map(user -> {
            review.setUser(user);
            review.setRestaurant_id(reviewDto.getRestaurantId());
            review.setRating(reviewDto.getRating());
            review.setReview(reviewDto.getReview());
            review.setLikes(reviewDto.getLikes());
            review.setIsDineIn(reviewDto.getIsDineIn());
            return restaurantReviewRepository.save(review);
        }).orElseThrow(() -> new ResourceNotFoundException("Not User found with id = " + review.getUser().getId()));

    }

    /**
     * @param id        except the reviewId , mandatory
     * @param reviewDto except and object with all the mandatory review information
     * @return updated review object
     * @throws ResourceNotFoundException if review is not found
     * @throws InvalidRequestException   if review is empty or null
     */
    @Override
    public Review updateReview(Long id, ReviewDto reviewDto) {

        Review review = restaurantReviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + id));

        if (reviewDto.getReview() == null || reviewDto.getReview().isEmpty() || reviewDto.getReview().isBlank()) {
            throw new InvalidRequestException("The review must be non-null");
        }

        if (reviewDto.getLikes() != null) {
            review.setLikes(reviewDto.getLikes());
        }
        if (reviewDto.getRating() != null) {
            review.setRating(reviewDto.getRating());
        }
        if (reviewDto.getReview() != null) {
            review.setReview(reviewDto.getReview());
        }
        if (reviewDto.getIsDineIn() != null) {
            review.setIsDineIn(reviewDto.getIsDineIn());
        }

        restaurantReviewRepository.save(review);
        return review;
    }

    /**
     * @param reviewId needed reviewId to delete
     * @throws InvalidRequestException if reviewId is empty or null
     */
    @Override
    public void deleteById(Long reviewId) {
        if (reviewId == null || reviewId == 0) throw new InvalidRequestException("The reviewId must be non-null or 0");
        restaurantReviewRepository.deleteById(reviewId);
    }

    /**
     * @param restaurant_id expect restaurant_id to fetch all the reviews
     * @return List<Review>
     * @throws InvalidRequestException if restaurant_id is empty or null
     */
    @Override
    public List<Review> findAllReviewByRestaurantId(Long restaurant_id) {
        if (restaurant_id == null || restaurant_id == 0)
            throw new InvalidRequestException("The restaurant_id must be non-null or 0");
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

