package com.restaurant.finder.responses.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @authorTeam-alpha
 * @Project spring-boot-library
 * @Date 25042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private int restaurantId;
    private Long reviewId;
    private Long userId;
    private String userName;
    private String comment;
    private boolean canEdit;
    private boolean canDelete;
}
