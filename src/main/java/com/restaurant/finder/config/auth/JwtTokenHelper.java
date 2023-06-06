package com.restaurant.finder.config.auth;

import com.restaurant.finder.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Team-alpha
 * @Project spring-boot-library
 * @Date 22042023
 * Copyright (C) 2023 Newcastle University, UK
 */

@Component
public class JwtTokenHelper {

    /*For which app the toke will be issued*/
    @Value("${jwt.auth.app}")
    private String appName;

    /*A unique secret key for generating and validating the token*/
    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    /*A time interval for which the token will be valid*/
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    /*A time interval for which the token will be valid*/
    @Value("${jwt.auth.refresh.token.expires_in}")
    private int refreshExpiration;

    /*The signature algorithm we are using ot create the token*/
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Autowired
    private TokenRepository tokenRepository;


    /*To get all the claims from the token, It's like encoding the token when the
     * request comes from the client , we can decrypt it.*/
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * These methods helps to get the sign-in key by decoding the secretkey
     * @return Key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /*Parse the token and get username from it.*/
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*When the first time user authenticate successfully we will create the token*/
    public String generateToken(String userName) {
        return buildToken(new HashMap<>(), userName, expiresIn);
    }

    public String generateRefreshToken(String userName) {
        return buildToken(new HashMap<>(), userName, refreshExpiration);
    }

    /**
     * @param extraClaims
     * @param userName
     * @param expiration
     * @return String build token which is generated
     */
    private String buildToken(Map<String, Object> extraClaims, String userName, long expiration) {
        return Jwts.builder()
                .setIssuer(appName)
                .setClaims(extraClaims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    /*Parse the token and generate expiration date and time.*/
    private Date generateExpirationDate(long expiresInDateTime) {
        return new Date(new Date().getTime() + expiresInDateTime);
    }

    /*When ever a request comes from the authenticated user we will validate if the
     * toke is valid or not.*/
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    /*To check is the token is expired or not.*/
    public boolean isTokenExpired(String token) {
        Date expireDate = null;
        try {
            expireDate = getExpirationDate(token);
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }

        return expireDate.before(new Date()) || tokenRepository.findByToken(token).get().isExpired();
    }


    /*To get the expiration date from the token*/
    private Date getExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*To get the Issue date from the token*/
    public Date getIssuedAtDateFromToken(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    /* A getter method to get the token from the request*/
    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new InputMismatchException("Header doesn't include the token");
        }
    }

    /**
     * @param request Http request
     * @return the auth header from the request provided
     */
    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
