package com.demo.ms_login.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtIssuer {

    private final SecretKey key;
    private final long minutes;

    public JwtIssuer(@Value("${app.jwt.secret}") String secret,
                     @Value("${app.jwt.expiration-minutes:60}") long minutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.minutes = minutes;
    }

    public String issue(Long userId, String username) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("username", username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(minutes * 60)))
            .signWith(key)
            .compact();
    }
}