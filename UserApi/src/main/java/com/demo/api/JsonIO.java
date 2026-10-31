package com.demo.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;


public final class JsonIO {
    private static final int MAX_BODY_CHARS = 10_000;

       
    public static JSONObject readBody(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            int ch;
            while ((ch = reader.read()) != -1) {
                if (sb.length() >= MAX_BODY_CHARS) return null;
                sb.append((char) ch);
            }
        }
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            return null;
        }
    }

    public static void write(HttpServletResponse resp, int status, JSONObject body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(body.toString());
    }

    public static void error(HttpServletResponse resp, int status, String message) throws IOException {
        write(resp, status, new JSONObject().put("error", message));
    }
}