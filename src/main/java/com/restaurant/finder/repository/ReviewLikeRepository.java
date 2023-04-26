package com.restaurant.finder.repository;

import com.restaurant.finder.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 16042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    List<ReviewLike> findAllByReviewId(Long reviewId);
}

