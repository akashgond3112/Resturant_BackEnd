package com.restaurant.finder.controller.user.auth;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.Token;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.enums.TokenType;
import com.restaurant.finder.repository.TokenRepository;
import com.restaurant.finder.service.user.UserService;
import com.restaurant.finder.service.user.UserServiceImpl;
import com.restaurant.finder.utilities.AuthenticationRequest;
import com.restaurant.finder.utilities.AuthenticationResponse;
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
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        /*Firs check if user has already logged in earlier has a token which is not expired and still valid*/
        List<Token> tokens = tokenRepository.findAllByUser(user);

        /*Here we will check if there is an assigned token for the user
         * If that token is neo */
        if (tokens.size() > 0) {
            for (Token token : tokens) {
                if (jwtTokenHelper.isTokenExpired(token.getToken())) {
                    String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
                    token.setRevoked(false);
                    token.setExpired(false);
                    token.setToken(jwtToken);
                    tokenRepository.save(token);
                    authenticationResponse.setAccessToken(token.getToken());
                    break;
                } else {
                    authenticationResponse.setAccessToken(token.getToken());
                }
            }
        } else {
            /*This only implies isf user is login for first time and there is no entry in the database*/
            String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
            Token token = Token.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
            authenticationResponse.setAccessToken(token.getToken());
        }

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("auth/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }


}
