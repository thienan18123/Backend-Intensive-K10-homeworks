package com.demo.ms_news.controller;

import com.demo.ms_login.security.VerifiedUser;
import com.demo.ms_news.dto.NewsDto;
import com.demo.ms_news.security.JwtFilter;
import com.demo.ms_news.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserNewsController {

    private final NewsService service;

    @GetMapping("/news/user/{userId}")
    public List<NewsDto> wishlist(@PathVariable Long userId, HttpServletRequest request) {

        VerifiedUser p = (VerifiedUser) request.getAttribute(JwtFilter.PRINCIPAL);

        if (p == null) {
            throw new UnauthorizedException("Missing or invalid access token");
        }
        if (!p.userId().equals(userId)) {
            throw new ForbiddenException("You can only read your own wishlist");
        }

        return service.findWishlistOf(userId);
    }
}