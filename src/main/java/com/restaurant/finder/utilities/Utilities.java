package com.restaurant.finder.utilities;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.InputMismatchException;

/**
 * @author akash.gond
 * @Project spring-boot-library
 * @Date 25042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Slf4j
public class Utilities {

    public static User getCurrentUser(JwtTokenHelper jwtTokenHelper, HttpServletRequest request, UserService userService) {
        final String token;
        final String userName;

        try {
            token = jwtTokenHelper.getToken(request);
        } catch (InputMismatchException inputMismatchException) {
            throw new TokeExpiredException("Valid token wan not found");
        }

        try {
            if (jwtTokenHelper.isTokenExpired(token)) {
                throw new TokeExpiredException("Token has been expired");
            }
        } catch (TokeExpiredException tokeExpiredException) {
            log.error(tokeExpiredException.getMessage());
        }
        userName = jwtTokenHelper.getUsernameFromToken(token);
        return (User) userService.loadUserByUsername(userName);
    }

    public static ResponseEntity<Object> validateIsTokeExpired(JwtTokenHelper jwtTokenHelper, HttpServletRequest request) {
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
        return null;
    }
}
