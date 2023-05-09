package com.restaurant.finder.service.restaurant.review.comment;

import com.restaurant.finder.dto.CommentDto;
import com.restaurant.finder.entity.Comment;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.InvalidRequestException;
import com.restaurant.finder.repository.CommentRepository;
import com.restaurant.finder.responses.comment.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    /**
     * Saves a new comment made by a user on a review.
     *
     * @param user       the user making the comment
     * @param review     the review on which the comment is being made
     * @param commentDto the DTO containing the comment content
     * @return the CommentResponse object for the new comment
     */
    @Override
    public CommentResponse saveComment(User user, Review review, CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setReview(review);
        comment.setUser(user);
        commentRepository.save(comment);
        return getCommentResponse(user, review, comment);
    }

    /**
     * A method to update an existing comment of a user on a specific review.
     *
     * @param commentId  the unique identifier of the comment to be updated.
     * @param commentDto a DTO containing the new comment details.
     * @param user       the user who is trying to update the comment.
     * @param review     the review on which the comment is made.
     * @return a CommentResponse object containing the updated comment details.
     * @throws NullPointerException    if any of the request parameters are null.
     * @throws InvalidRequestException if no comment is found with the matching commentId and userId combination.
     */
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

    /**
     * Deletes the comment with the given commentId if it exists and is associated with the given user.
     *
     * @param commentId the ID of the comment to be deleted
     * @param user      the user associated with the comment to be deleted
     * @throws NullPointerException    if the commentId is null or 0
     * @throws InvalidRequestException if the comment with the given ID is not associated with the given user
     */
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

    /**
     * Returns a list of CommentResponse objects corresponding to all comments associated with the given review ID.
     * Throws an InvalidRequestException if the review ID is null or zero.
     *
     * @param reviewId the ID of the review associated with the comments
     * @param user     the user making the request
     * @return a List of CommentResponse objects containing the comments associated with the given review ID
     * @throws InvalidRequestException if the review ID is null or zero
     */
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

    /**
     * Returns a list of CommentResponse objects corresponding to all comments associated with the given review ID.
     * Throws an InvalidRequestException if the review ID is null or zero.
     *
     * @param reviewId the ID of the review associated with the comments
     * @return a List of CommentResponse objects containing the comments associated with the given review ID
     * @throws InvalidRequestException if the review ID is null or zero
     */
    @Override
    public List<CommentResponse> findAllCommentByReviewId(Long reviewId) {

        if (reviewId == null || reviewId == 0)
            throw new InvalidRequestException("The reviewId must not be non-null or 0");
        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentRepository.findAllByReviewId(reviewId).forEach(comment -> {
            commentResponseList.add(getCommentResponse(null, comment.getReview(), comment));
        });

        return commentResponseList;
    }

    /**
     * Returns a {@link CommentResponse} object that contains the comment details, such as the restaurant id, review id,
     * user id, username, comment, and whether the current user can edit and delete the comment.
     *
     * @param user    the user who made the comment
     * @param review  the review the comment is associated with
     * @param comment the comment being returned
     * @return a {@link CommentResponse} object that contains the comment details and permissions
     */
    private CommentResponse getCommentResponse(User user, Review review, Comment comment) {
        return CommentResponse.builder()
                .restaurantId(review.getRestaurant_id())
                .reviewId(review.getId())
                .userId(user.getId())
                .userName(user.getUsername())
                .comment(comment.getComment())
                .canEdit(user != null && comment.getUser().getId().equals(user.getId()))
                .canDelete(user != null && comment.getUser().getId().equals(user.getId()))
                .timePast(daysAgo(comment.getCreated_at())).build();
    }

    public static long daysAgo(LocalDateTime date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return Duration.between(date, currentDateTime).toDays();
    }
}

