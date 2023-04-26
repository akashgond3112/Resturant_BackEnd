package com.restaurant.finder.service.user;

import com.restaurant.finder.config.auth.JwtTokenHelper;
import com.restaurant.finder.entity.Token;
import com.restaurant.finder.entity.User;
import com.restaurant.finder.repository.TokenRepository;
import com.restaurant.finder.utilities.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 23042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        User user = Utilities.getCurrentUser(jwtTokenHelper, request, userService);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        List<Token> tokens = tokenRepository.findAllByUser(user);
        tokens.forEach(token -> {
            if (token.getToken().equals(jwt)) {
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
                SecurityContextHolder.clearContext();
            }
        });


    }
}
