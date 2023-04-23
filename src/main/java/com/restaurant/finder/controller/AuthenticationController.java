package com.restaurant.finder.controller;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.dto.UserDto;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.service.user.UserServiceImpl;
import com.restaurant.finder.utilities.AuthenticationRequest;
import com.restaurant.finder.utilities.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

/**
 * @author akash.gond
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
    private UserServiceImpl userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        try {
            String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
            authenticationResponse.setAccessToken(jwtToken);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<UserDto> getUserInfo(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUsername());
        userDto.setGender(user.getGender());
        userDto.setMobileNumber(userDto.getMobileNumber());

        return ResponseEntity.ok(userDto);
    }
}
