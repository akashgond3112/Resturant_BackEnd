package com.restaurant.finder.controller.user.info;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.enums.Role;
import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.InputMismatchException;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23042023
 * Copyright (C) 2023 Newcastle University, UK
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request,
                                               HttpServletResponse response) {

        final String token;
        final String userName;

        try {
            token = jwtTokenHelper.getToken(request);
        } catch (InputMismatchException inputMismatchException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            if (jwtTokenHelper.isTokenExpired(token)) {
                throw new TokeExpiredException("Token has been expired");
            }
        }catch (TokeExpiredException tokeExpiredException){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token has been expired");
        }

        userName = jwtTokenHelper.getUsernameFromToken(token);

        User user = (User) userService.loadUserByUsername(userName);

        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setUserName(user.getUsername());
            userDto.setGender(user.getGender());
            userDto.setMobileNumber(user.getMobileNumber());

            return ResponseEntity.ok(userDto);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("auth/users")
    public ResponseEntity<List<User>> getUsers(Principal principal) {

        User user = (User) userService.loadUserByUsername(principal.getName());
        if (user.getRole().equals(Role.ADMIN)) {
            List<User> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
