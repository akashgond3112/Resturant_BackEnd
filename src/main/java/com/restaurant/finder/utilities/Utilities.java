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
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 25042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Slf4j
public class Utilities {

    /**
     * @param jwtTokenHelper expect jwtTokenHelper object
     * @param request        expect the HttpServletRequest
     * @param userService    expect userService object
     * @return the user entity object if the token in not expired
     * @throws InputMismatchException if we cannot get the token from the request
     * @throws TokeExpiredException   if the token is expired
     */
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


    /**
     * Validates whether the provided JWT token in the HTTP request is expired or not.
     * If the token is expired, a TokeExpiredException is thrown, otherwise null is returned.
     *
     * @param jwtTokenHelper An instance of JwtTokenHelper used to validate the token.
     * @param request        The HttpServletRequest containing the token to be validated.
     * @return A ResponseEntity object with HTTP status code 403 (Forbidden) and a message
     * indicating that the token has expired, or null if the token is valid.
     * If the request's token is malformed or null, a ResponseEntity with HTTP status code 400 (Bad Request)
     * is returned.
     * @throws InputMismatchException if we cannot get the token from the request
     * @throws TokeExpiredException   if the token is expired
     */
    public static ResponseEntity<Object> validateIsTokeExpired(JwtTokenHelper jwtTokenHelper, HttpServletRequest request) {
        final String token;
        final String userName;

        try {
            token = jwtTokenHelper.getToken(request);
        } catch (InputMismatchException inputMismatchException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            if (jwtTokenHelper.isTokenExpired(token)) {
                throw new TokeExpiredException("Token has been expired");
            }
        } catch (TokeExpiredException tokeExpiredException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token has been expired");
        }
        return null;
    }
}
