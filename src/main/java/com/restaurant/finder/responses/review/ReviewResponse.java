package com.restaurant.finder.responses.review;

import com.restaurant.finder.responses.comment.CommentResponse;
import com.restaurant.finder.responses.likes.ReviewLikeResponse;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 25042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long reviewId;
    private Long userId;
    private String userName;
    private int restaurantId;
    private Integer rating;
    private String review;
    private List<ReviewLikeResponse> likes;
    private List<CommentResponse> comments;
    private String deliveryAvailable;
    private String dineInAvailable;
    private boolean canEdit;
    private boolean canDelete;
}
