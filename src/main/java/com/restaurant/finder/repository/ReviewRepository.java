package com.restaurant.finder.repository;

import com.restaurant.finder.entity.Review;
import com.restaurant.finder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.restaurant_id = :restaurantId")
    List<Review> findReviewsByRestaurant_id(@Param("restaurantId") String restaurantId);

    Review findByIdAndUserId(Long id, Long user_id );


}
