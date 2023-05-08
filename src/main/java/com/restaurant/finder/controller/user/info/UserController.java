package com.restaurant.finder.controller.user.info;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.enums.Role;
import com.restaurant.finder.responses.user.UserResponse;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * @param request request include the request having the token
     * @return the userDto object having the user details
     */
    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {

        ResponseEntity<Object> objectResponseEntity = Utilities.validateIsTokeExpired(jwtTokenHelper, request);
        if (objectResponseEntity != null) {
            return objectResponseEntity;
        }

        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        if (user != null) {
            return ResponseEntity.ok(UserResponse.builder()
                    .userId(user.getId())
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .mobileNumber(user.getMobileNumber()).build());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param principal expect a current user principal object
     * @return List<User></User> only the role of the user is admin else return forbidden error
     */
    @GetMapping("auth/users")
    public ResponseEntity<List<UserResponse>> getUsers(Principal principal) {

        User matchingUser = (User) userService.loadUserByUsername(principal.getName());
        if (matchingUser.getRole().equals(Role.ADMIN)) {
            List<User> users = userService.findAllUsers();
            List<UserResponse> userResponseList = new ArrayList<>();
            users.forEach(user -> {
                userResponseList.add(
                        UserResponse.builder()
                                .userId(user.getId())
                                .userName(user.getUsername())
                                .email(user.getEmail())
                                .gender(user.getGender())
                                .mobileNumber(user.getMobileNumber()).build()
                );
            });
            return new ResponseEntity<>(userResponseList, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
