package com.demo.controller;

import com.demo.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public final class RefreshCookieController {
    private static final String NAME = "Rft";

    private RefreshCookieController() { }

    public static void set(HttpServletRequest req, HttpServletResponse resp, String token) {
        resp.addCookie(cookie(req, token, (int) RefreshTokenService.TTL_SECONDS));
    }

   
    public static String read(HttpServletRequest req) {
        if (req.getCookies() == null) return null;
        for (Cookie c : req.getCookies()) {
            if (NAME.equals(c.getName()) && !c.getValue().isEmpty()) return c.getValue();
        }
        return null;
    }

    public static void clear(HttpServletRequest req, HttpServletResponse resp) {
        resp.addCookie(cookie(req, "", 0));
    }

    private static Cookie cookie(HttpServletRequest req, String value, int maxAge) {
        Cookie c = new Cookie(NAME, value);
        c.setHttpOnly(true);                                  
        c.setSecure(req.isSecure());                           
        c.setPath(req.getContextPath() + "/api/auth");        
        c.setMaxAge(maxAge);                                   
        c.setAttribute("SameSite", "Strict");                  
        return c;
    }
}