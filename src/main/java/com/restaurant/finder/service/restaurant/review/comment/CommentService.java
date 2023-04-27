package com.restaurant.finder.service.restaurant.review.comment;

import com.restaurant.finder.dto.CommentDto;
import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.responses.comment.CommentResponse;

import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */

/**
 * A service interface for handling comments related operations for a restaurant review system.
 */
public interface CommentService {
    CommentResponse saveComment(User user, Review review, CommentDto commentDto);

    CommentResponse updateComment(Long commentId, CommentDto commentDto, User user, Review review);

    void deleteCommentById(Long commentId, User user);

    List<CommentResponse> findAllCommentByReviewId(Long reviewId, User user);

}
