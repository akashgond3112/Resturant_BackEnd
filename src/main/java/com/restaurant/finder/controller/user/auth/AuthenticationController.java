package com.restaurant.finder.controller.user.auth;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.entity.Token;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.enums.TokenType;
import com.restaurant.finder.repository.TokenRepository;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.responses.authentication.AuthenticationRequest;
import com.restaurant.finder.responses.authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * @param authenticationRequest which include the username and password of the user
     * @return the generated token as AuthenticationResponse
     */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userService.login(authenticationRequest, authenticationManager));
    }

    /**
     * @param request  request include the current token of the user
     * @param response response as refresh token
     * @throws IOException if we cannot find the matching token
     */
    @PostMapping("auth/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }


}
