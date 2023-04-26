package com.restaurant.finder.service.restaurant.review.comment;

import com.restaurant.finder.dto.CommentDto;
import com.restaurant.finder.dto.ReviewDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.ReviewLike;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.repository.ReviewLikeRepository;
import com.restaurant.finder.repository.ReviewRepository;
import com.restaurant.finder.repository.UserRepository;
import com.restaurant.finder.responses.review.CommentResponse;
import com.restaurant.finder.responses.review.ReviewResponse;
import com.restaurant.finder.service.restaurant.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private ReviewRepository restaurantReviewRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentResponse saveComment(User user, Review review, CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setReview(review);
        comment.setUser(user);
        commentRepository.save(comment);
        return getCommentResponse(user, review, comment);
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentDto commentDto, User user, Review review) {

        if (commentId == null || commentDto.getReviewId() == null ||
                commentDto.getUserId() == null || commentDto.getComment().isBlank() || commentDto.getComment().isBlank()) {
            throw new NullPointerException("The review request must be non-null");
        }

        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (comment == null) {
            throw new InvalidRequestException("Cannot find comment with matching comment Id and user Id while updating comment!!");
        }

        if (commentDto.getComment() != null) {
            comment.setComment(commentDto.getComment());
        }

        commentRepository.save(comment);
        return getCommentResponse(user, review, comment);
    }

    @Override
    public void deleteCommentById(Long commentId, User user) {

        if (commentId == null || commentId == 0)
            throw new NullPointerException("The commentId cannot be non-null or 0");
        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (comment == null) {
            throw new InvalidRequestException("Cannot find comment with matching comment Id and user Id while deleting review!!");
        }
        commentRepository.deleteById(commentId);

    }

    @Override
    public List<CommentResponse> findAllCommentByReviewId(Long reviewId, User user) {

        if (reviewId == null || reviewId == 0)
            throw new InvalidRequestException("The reviewId must not be non-null or 0");
        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentRepository.findAllByReviewId(reviewId).forEach(comment -> {
            commentResponseList.add(getCommentResponse(user, comment.getReview(), comment));
        });

        return commentResponseList;
    }


    private CommentResponse getCommentResponse(User user, Review review, Comment comment) {
        return CommentResponse.builder()
                .restaurantId(review.getRestaurant_id())
                .reviewId(review.getId())
                .userId(user.getId())
                .userName(user.getUsername())
                .comment(comment.getComment())
                .canEdit(comment.getUser().getId().equals(user.getId()))
                .canDelete(comment.getUser().getId().equals(user.getId())).build();
    }

}

