package com.demo.filter;

import com.demo.api.JsonIO;
import com.demo.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;


@WebFilter("/api/*")
public class JwtAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean isPublic = path.startsWith("/api/auth/")                       
                || (path.startsWith("/api/news/") && "GET".equals(req.getMethod()));    
        if (isPublic) {
            chain.doFilter(request, response);                  
            return;
        }

        String header = req.getHeader("Authorization");
        String token = (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
        JSONObject claims = JwtUtil.verify(token);
        if (claims == null) {
            resp.setHeader("WWW-Authenticate", "Bearer");
            JsonIO.error(resp, HttpServletResponse.SC_UNAUTHORIZED, "Missing, invalid or expired token");
            return;                                             
        }
        req.setAttribute("jwtClaims", claims);                  
        chain.doFilter(request, response);
    }
}