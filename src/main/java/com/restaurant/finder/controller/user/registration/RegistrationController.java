package com.restaurant.finder.controller.user.registration;

import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    private UserService userService;

    /**
     * @param userDto expect and userDTO object to for the user registration
     * @return the AuthenticationResponse object
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check all the fields is available!");

        return ResponseEntity.ok(userService.register(userDto));

    }
}
