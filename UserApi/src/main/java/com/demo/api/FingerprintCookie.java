package com.demo.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;


public final class FingerprintCookie {
    private static final String NAME = "Fgp";
    private static final SecureRandom RANDOM = new SecureRandom();

    private FingerprintCookie() { }

  
    public static String issue(HttpServletRequest req, HttpServletResponse resp) {
        byte[] random = new byte[32];
        RANDOM.nextBytes(random);
        String fingerprint = HexFormat.of().formatHex(random);
        resp.addCookie(cookie(req, fingerprint, -1));         
        return sha256(fingerprint);
    }

  
    public static String hashFromRequest(HttpServletRequest req) {
        if (req.getCookies() == null) return null;
        for (Cookie c : req.getCookies()) {
            if (NAME.equals(c.getName())) return sha256(c.getValue());
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
        c.setPath(req.getContextPath() + "/api");              
        c.setMaxAge(maxAge);
        c.setAttribute("SameSite", "Strict");                
        return c;
    }

    static String sha256(String value) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}