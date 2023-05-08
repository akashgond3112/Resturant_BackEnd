package com.restaurant.finder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023 Copyright (C) 2023 Newcastle University, UK
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String restaurant_id;

    @Column(nullable = false, name = "rating")
    private Integer rating;

    @Column(nullable = false, name = "review")
    private String review;

    @Column(nullable = true)
    private Integer likes = 0;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(nullable = false,updatable = true)
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(nullable = true, name = "dine_in_available")
    private String dineInAvailable;

    @Column(nullable = true, name = "delivery_available")
    private String deliveryAvailable;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLike> reviewLikes;
    @PrePersist
    public void onPrePersist() {
        this.created_at = LocalDateTime.now();
    }

}

