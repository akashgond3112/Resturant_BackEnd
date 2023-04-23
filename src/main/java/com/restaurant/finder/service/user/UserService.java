package com.restaurant.finder.service.user;

import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;

import java.util.List;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */
public interface UserService {

    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<User> findAllUsers();
}
