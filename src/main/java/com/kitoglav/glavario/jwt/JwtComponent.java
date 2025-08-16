package com.kitoglav.glavario.jwt;

import com.kitoglav.glavario.jpa.models.Role;
import com.kitoglav.glavario.jpa.models.user.User;
import com.kitoglav.glavario.rest.ApiResponseException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtComponent {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    private final UserDetailsService userDetailsService;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(Role.class::cast).map(Role::convert).collect(Collectors.toSet()));
        claims.put("username", userDetails.getUsername());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public User getByToken(String token) {
        return getByToken(token, false);
    }

    public User getByToken(String token, boolean req) {
        if (token == null) {
            if (req) {
                throw new ApiResponseException(HttpStatus.UNAUTHORIZED, "Нет данных об авторизации");
            } else {
                return null;
            }
        }
        if (!validateToken(token)) {
            throw new ApiResponseException(HttpStatus.UNAUTHORIZED, "Недействительный токен");
        }
        String username = getUsernameFromToken(token);
        return (User) userDetailsService.loadUserByUsername(username);
    }
}
