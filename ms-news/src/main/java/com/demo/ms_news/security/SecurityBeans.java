package com.demo.ms_news.security;

import com.demo.ms_login.security.JwtVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class SecurityBeans {

    @Bean
    public JwtVerifier jwtVerifier(@Value("${app.jwt.secret}") String secret) {
        return new JwtVerifier(secret);
    }
}