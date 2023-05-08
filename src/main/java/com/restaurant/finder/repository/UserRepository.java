package com.restaurant.finder.repository;

import com.restaurant.finder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 15042023
 *       Copyright (C) 2023 Newcastle University, UK
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByUsername(String userName);
}
