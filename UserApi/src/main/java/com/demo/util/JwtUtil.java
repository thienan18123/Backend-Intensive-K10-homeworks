package com.demo.util;

import com.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;


public final class JwtUtil {
    private static final long TTL_SECONDS = 5 * 60;            
    private static final SecretKey KEY = loadKey();

 
    private static final JwtParser PARSER = Jwts.parser()
            .verifyWith(KEY)                                    
            .build();

  

    public static long ttlSeconds() { return TTL_SECONDS; }

   
    public static String createToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))          
                .claim("loginId", user.getLoginId())
                .claim("name", user.getName())               
                .issuedAt(Date.from(now))                       
                .expiration(Date.from(now.plusSeconds(TTL_SECONDS)))   
                .signWith(KEY, Jwts.SIG.HS256)                  
                .compact();                                    
    }

    
    public static JSONObject verify(String token) {
        if (token == null || token.isBlank()) return null;
        try {
            
            Claims c = PARSER.parseSignedClaims(token).getPayload();
            if (c.getExpiration() == null) return null;         

            return new JSONObject()
                    .put("sub", c.getSubject())
                    .put("loginId", c.get("loginId", String.class))
                    .put("name", c.get("name", String.class))
                    .put("fgp", c.get("fgp", String.class))
                    .put("exp", c.getExpiration().getTime() / 1000);
        } catch (JwtException | IllegalArgumentException e) {   
            return null;
        }
    }

    private static SecretKey loadKey() {
        String value = System.getenv("JWT_SECRET");
        if (value == null || value.length() < 32) {
            throw new IllegalStateException("Environment variable JWT_SECRET must be at least 32 characters");
        }
    
        return Keys.hmacShaKeyFor(value.getBytes(StandardCharsets.UTF_8));
    }
}