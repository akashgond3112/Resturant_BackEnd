package com.restaurant.finder.service.restaurant.review.like;

import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.ReviewLikeRepository;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 26042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Service
public class LikeServiceImplement implements LikeService {

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    /**
     * Saves a new review like for a given user and review.
     *
     * @param user   the user who is performing the like action
     * @param review the review which is being liked
     * @return the response object containing the review like details
     */
    @Override
    public ReviewLikeResponse saveReviewLike(User user, Review review) {

        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setReview(review);
        reviewLike.setUser(user);
        reviewLikeRepository.save(reviewLike);
        return getReviewLikeResponse(user, review, reviewLike);
    }

    /**
     * Deletes a review like by like Id and user Id.
     *
     * @param likeId The id of the like to be deleted.
     * @param user   The user who is deleting the like.
     * @throws NullPointerException    if likeId is null or equal to 0.
     * @throws InvalidRequestException if reviewLike with the matching likeId and userId cannot be found.
     */
    @Override
    public void deleteReviewLikeById(Long likeId, User user) {
        if (likeId == null || likeId == 0)
            throw new NullPointerException("The likeId cannot be non-null or 0");

        ReviewLike reviewLike = reviewLikeRepository.findByIdAndUserId(likeId, user.getId());

        if (reviewLike == null) {
            throw new InvalidRequestException("Cannot find like with matching like Id and user Id while deleting like!!");
        }

        reviewLikeRepository.deleteById(likeId);
    }

    /**
     * Retrieves a list of ReviewLikeResponse objects based on the given review ID and user.
     *
     * @param reviewId the ID of the review to search for review likes
     * @param user     the user who is making the request
     * @return a list of ReviewLikeResponse objects
     * @throws InvalidRequestException if the reviewId is null or 0
     */
    @Override
    public List<ReviewLikeResponse> findAllReviewLikeByReviewId(Long reviewId, User user) {

        if (reviewId == null || reviewId == 0)
            throw new InvalidRequestException("The reviewId must not be non-null or 0");

        List<ReviewLikeResponse> reviewLikeResponses = new ArrayList<>();

        reviewLikeRepository.findAllByReviewId(reviewId).forEach(reviewLike -> {
            reviewLikeResponses.add(getReviewLikeResponse(user, reviewLike.getReview(), reviewLike));
        });

        return reviewLikeResponses;
    }

    /**
     * Retrieves a list of ReviewLikeResponse objects based on the given review ID.
     *
     * @param reviewId the ID of the review to search for review likes
     * @return a list of ReviewLikeResponse objects
     * @throws InvalidRequestException if the reviewId is null or 0
     */
    @Override
    public List<ReviewLikeResponse> findAllReviewLikeByReviewId(Long reviewId) {

        if (reviewId == null || reviewId == 0)
            throw new InvalidRequestException("The reviewId must not be non-null or 0");

        List<ReviewLikeResponse> reviewLikeResponses = new ArrayList<>();

        reviewLikeRepository.findAllByReviewId(reviewId).forEach(reviewLike -> {
            reviewLikeResponses.add(getReviewLikeResponse(null, reviewLike.getReview(), reviewLike));
        });

        return reviewLikeResponses;
    }

    /**
     * Generates a {@link ReviewLikeResponse} object for a given user, review, and review like.
     * The method takes in the user, review, and review like information and constructs a ReviewLikeResponse object
     * using the data. The method determines if the user can remove the like by checking if the user id of the review like
     * matches the user id provided.
     *
     * @param user       the user who liked the review
     * @param review     the review for which the like was given
     * @param reviewLike the ReviewLike object containing the like information
     * @return a ReviewLikeResponse object containing the like information and user information
     */
    private ReviewLikeResponse getReviewLikeResponse(User user, Review review, ReviewLike reviewLike) {
        return ReviewLikeResponse.builder()
                .restaurantId(review.getRestaurant_id())
                .reviewId(review.getId())
                .reviewLikeId(reviewLike.getId())
                .userId(reviewLike.getUser().getId())
                .userName(reviewLike.getUser().getUsername())
                .canRemoveLike(user != null &&  reviewLike.getUser().getId().equals(user.getId()))
                .timePast(daysAgo(reviewLike.getCreated_at())).build();
    }

    public static long daysAgo(LocalDateTime date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return Duration.between(date, currentDateTime).toDays();
    }
}
