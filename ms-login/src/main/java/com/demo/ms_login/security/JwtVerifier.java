package com.demo.ms_login.security;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtVerifier {

    private final SecretKey key;

    public JwtVerifier(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Optional<VerifiedUser> verify(String token) {
        try {
            Claims c = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return Optional.of(new VerifiedUser(
                Long.valueOf(c.getSubject()),
                c.get("username", String.class)));

        } catch (JwtException | IllegalArgumentException ex) {
            return Optional.empty();   
        }
    }
}