package com.restaurant.finder.config.auth;

import com.restaurant.finder.exception.TokeExpiredException;
import com.restaurant.finder.repository.TokenRepository;
import com.restaurant.finder.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;


    /**
     * The filter retrieves the JWT token from the request headers and transfers authentication to the injected AuthenticationManager in this class,
     * which serves as the starting point of our JWT authentication process.
     * An exception is thrown if the token cannot be located, which prevents the request from being processed.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * @throws TokeExpiredException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth") || request.getServletPath().contains("/restaurant/") || request.getServletPath().contains("/restaurants/")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = jwtTokenHelper.getAuthHeaderFromHeader(request);
        userEmail = jwtTokenHelper.getUsernameFromToken(jwt.substring("Bearer ".length()));

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(userEmail);
            if (Boolean.TRUE.equals(jwtTokenHelper.validateToken(jwt, userDetails))) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                throw new TokeExpiredException("Token has been expired!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
