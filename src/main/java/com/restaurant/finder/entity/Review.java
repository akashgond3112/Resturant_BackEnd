package com.restaurant.finder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 15042023 Copyright (C) 2023 Newcastle University, UK
 */
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //	@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private Long restaurant_id;

    @Column(nullable = false, name = "rating")
    private Integer rating;

    @Column(nullable = true, name = "review")
    private String review;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(nullable = false,updatable = true)
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @PrePersist
    public void onPrePersist() {
        this.created_at = LocalDateTime.now();
    }


}

