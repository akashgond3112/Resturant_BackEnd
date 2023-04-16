package com.restaurant.finder.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 16042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@Entity
@Table(name = "review_likes")
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(nullable = false,updatable = true)
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // getters and setters
}
