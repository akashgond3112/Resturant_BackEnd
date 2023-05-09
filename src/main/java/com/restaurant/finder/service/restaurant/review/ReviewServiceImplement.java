package com.restaurant.finder.service.restaurant.review;

import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.repository.ReviewLikeRepository;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.responses.comment.CommentResponse;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;
import com.restaurant.finder.responses.review.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Service
public class ReviewServiceImplement implements ReviewService {

    @Autowired
    private ReviewRepository restaurantReviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewLikeRepository reviewLikeRepository;
    // Create DateTimeFormatter instance with specified format
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * @param reviewDto except and object with all the mandatory review information
     * @return saved review object
     * @throws ResourceNotFoundException if user is not found
     */
    @Override
    public ReviewResponse saveReview(User user, ReviewDto reviewDto) {

        Review review = new Review();
        review.setUser(user);
        review.setRestaurant_id(reviewDto.getRestaurantId());
        review.setRating(reviewDto.getRating());
        review.setReview(reviewDto.getReview());
        review.setLikes(reviewDto.getLikes());
        review.setDineInAvailable(reviewDto.getDineInAvailable().isEmpty() ? "YES" : reviewDto.getDineInAvailable());
        review.setDeliveryAvailable(reviewDto.getDeliveryAvailable().isEmpty() ? "NO" : reviewDto.getDeliveryAvailable());
        restaurantReviewRepository.save(review);

        return getReviewResponse(user, review);
    }

    /**
     * @param id        except the reviewId , mandatory
     * @param reviewDto except and object with all the mandatory review information
     * @return updated review object
     * @throws ResourceNotFoundException if review is not found
     * @throws InvalidRequestException   if review is empty or null
     */
    @Override
    public ReviewResponse updateReview(Long id, ReviewDto reviewDto, User user) {

        if (id == null || reviewDto.getReview() == null ||
                reviewDto.getReview().isEmpty() || reviewDto.getReview().isBlank()) {
            throw new NullPointerException("The review request must be non-null");
        }

        /*Check for an entry if the same user has written the review or not!*/
        Review review = restaurantReviewRepository.findByIdAndUserId(id, user.getId());

        if (review == null) {
            throw new InvalidRequestException("Cannot find review with matching review Id and user Id while updating review!!");
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
        if (reviewDto.getDineInAvailable() != null) {
            review.setDineInAvailable(reviewDto.getDineInAvailable());
        }
        if (reviewDto.getDeliveryAvailable() != null) {
            review.setDeliveryAvailable(reviewDto.getDeliveryAvailable());
        }

        restaurantReviewRepository.save(review);
        return getReviewResponse(user, review);
    }

    /**
     * @param reviewId needed reviewId to delete
     * @throws InvalidRequestException if reviewId is empty or null
     */
    @Override
    public void deleteById(Long reviewId, User user) {

        if (reviewId == null || reviewId == 0) throw new NullPointerException("The reviewId cannot be non-null or 0");

        /*Check for an entry if the same user has written the review or not!*/
        Review review = restaurantReviewRepository.findByIdAndUserId(reviewId, user.getId());

        if (review == null) {
            throw new InvalidRequestException("Cannot find review with matching review Id and user Id while deleting review!!");
        }

        restaurantReviewRepository.deleteById(review.getId());
    }

    /**
     * @param restaurant_id expect restaurant_id to fetch all the reviews
     * @return List<Review>
     * @throws InvalidRequestException if restaurant_id is empty or null
     */
    @Override
    public List<ReviewResponse> findAllReviewByRestaurantId(String restaurant_id, User user) {
        if (restaurant_id == null || restaurant_id.isEmpty())
            throw new InvalidRequestException("The restaurant_id must not be non-null or 0");

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        restaurantReviewRepository.findReviewsByRestaurant_id(restaurant_id).forEach(review -> {
            reviewResponseList.add(getReviewResponse(user, review));
        });
        return reviewResponseList;
    }

    /**
     * @param restaurant_id expect restaurant_id to fetch all the reviews for the given restaurant ID
     * @return List<Review>
     * @throws InvalidRequestException if restaurant_id is empty or null
     */
    @Override
    public List<ReviewResponse> findAllReviewByRestaurantId(String restaurant_id) {
        if (restaurant_id == null || restaurant_id.isEmpty())
            throw new InvalidRequestException("The restaurant_id must not be non-null or 0");

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        restaurantReviewRepository.findReviewsByRestaurant_id(restaurant_id).forEach(review -> {
            reviewResponseList.add(getReviewResponse(null, review));
        });
        return reviewResponseList;
    }

    private ReviewResponse getReviewResponse(User user, Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .review(review.getReview())
                .userId(review.getUser().getId())
                .userName(review.getUser().getUsername())
                .restaurantId(review.getRestaurant_id())
                .rating(review.getRating())
                .comments(getCommentResponseList(user, review))
                .likes(getReviewLikeResponseList(user, review))
                .deliveryAvailable(review.getDeliveryAvailable())
                .dineInAvailable(review.getDineInAvailable())
                .canEdit(user != null && review.getUser().getId().equals(user.getId()))
                .canDelete(user != null && review.getUser().getId().equals(user.getId()))
                .createdDate(review.getCreated_at().format(dateTimeFormatter))
                .timePast(daysAgo(review.getCreated_at())).build();
    }

    private List<CommentResponse> getCommentResponseList(User user, Review review) {

        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentRepository.findAllByReviewId(review.getId()).forEach(comment -> {
            commentResponseList.add(CommentResponse.builder()
                    .restaurantId(review.getRestaurant_id())
                    .reviewId(review.getId())
                    .userId(user.getId())
                    .userName(user.getUsername())
                    .comment(comment.getComment())
                    .canEdit(user != null && comment.getUser().getId().equals(user.getId()))
                    .canDelete(user != null && comment.getUser().getId().equals(user.getId()))
                    .createdDate(comment.getCreated_at().format(dateTimeFormatter))
                    .timePast(daysAgo(comment.getCreated_at())).build());
        });
        return commentResponseList;
    }

    private List<ReviewLikeResponse> getReviewLikeResponseList(User user, Review review) {
        List<ReviewLikeResponse> reviewLikeResponses = new ArrayList<>();

        reviewLikeRepository.findAllByReviewId(review.getId()).forEach(reviewLike -> {
            reviewLikeResponses.add(
                    ReviewLikeResponse.builder()
                            .restaurantId(review.getRestaurant_id())
                            .reviewId(review.getId())
                            .reviewLikeId(reviewLike.getId())
                            .userId(user.getId())
                            .userName(user.getUsername())
                            .canRemoveLike(user != null && reviewLike.getUser().getId().equals(user.getId()))
                            .createdDate(reviewLike.getCreated_at().format(dateTimeFormatter))
                            .timePast(daysAgo(review.getCreated_at())).build()
            );
        });
        return reviewLikeResponses;

    }

    public static long daysAgo(LocalDateTime date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return Duration.between(date, currentDateTime).toDays();
    }
}

