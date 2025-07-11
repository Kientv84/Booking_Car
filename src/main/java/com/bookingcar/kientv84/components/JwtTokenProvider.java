package com.bookingcar.kientv84.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "my-super-secret-jwt-key-123456789012345";

    private static final long EXPIRATION_TIME = 3000;

    private static final String ROLES = "roles";

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        Object roles = getClaims(token).get(ROLES);
        return roles instanceof List<?> ?
                (((List<?>) roles).stream()).map(Object::toString).toList():List.of();
    }

    public  boolean validateToken(String token) {
        try {
            getClaims((token));
            return true;
        } catch ( JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
