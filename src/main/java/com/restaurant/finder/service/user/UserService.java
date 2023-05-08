package com.restaurant.finder.service.user;

import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.responses.authentication.AuthenticationRequest;
import com.restaurant.finder.responses.authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */
public interface UserService {

    AuthenticationResponse register(UserDto userDto);
    AuthenticationResponse login(AuthenticationRequest request, AuthenticationManager authenticationManager);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    UserDetails loadUserByUsername(String username);

    void refreshToken(HttpServletRequest request,
                      HttpServletResponse response);


}
