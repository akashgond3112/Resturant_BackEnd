package com.restaurant.finder.service.restaurant.review.like;

import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.ReviewLikeRepository;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ReviewLikeResponse saveReviewLike(User user, Review review) {

        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setReview(review);
        reviewLike.setUser(user);
        reviewLikeRepository.save(reviewLike);
        return getReviewLikeResponse(user, review, reviewLike);
    }

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


    private ReviewLikeResponse getReviewLikeResponse(User user, Review review, ReviewLike reviewLike) {
        return ReviewLikeResponse.builder()
                .restaurantId(review.getRestaurant_id())
                .reviewId(review.getId())
                .reviewLikeId(reviewLike.getId())
                .userId(user.getId())
                .userName(user.getUsername())
                .canRemoveLike(reviewLike.getUser().getId().equals(user.getId())).build();
    }

}
