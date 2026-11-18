package com.demo.ms_news.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    public static final String PRINCIPAL = "principal";

    private final TokenChecker checker;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) req;
        String header = http.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7).trim();
            checker.check(token).ifPresent(p -> http.setAttribute(PRINCIPAL, p));
        }

        chain.doFilter(req, res);   
    }
}