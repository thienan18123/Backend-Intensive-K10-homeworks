package com.demo.controller;

import com.demo.model.User;
import com.demo.service.UserService;
import com.demo.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/api/auth/*")
public class AuthApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final transient UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();                     

        if ("/logout".equals(action)) {
            FingerprintCookie.clear(req, resp);                 
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        JSONObject body = JsonIO.readBody(req);
        if (body == null) {
            JsonIO.error(resp, HttpServletResponse.SC_BAD_REQUEST, "Body must be valid JSON");
            return;
        }

        try {
            if ("/login".equals(action)) {
                login(body, req, resp);
            } else if ("/register".equals(action)) {
                register(body, resp);
            } else {
                JsonIO.error(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown endpoint");
            }
        } catch (SQLException e) {
            throw new ServletException("Auth API failed", e);
        }
    }

    private void login(JSONObject body, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, SQLException {
        User user = userService.login(body.optString("loginId", null), body.optString("password", null));
        if (user == null) {
            JsonIO.error(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong ID or password");
            return;
        }
        JSONObject result = new JSONObject()
                .put("token", JwtUtil.createToken(user, FingerprintCookie.issue(req, resp)))
                .put("tokenType", "Bearer")
                .put("expiresIn", JwtUtil.ttlSeconds());
        JsonIO.write(resp, HttpServletResponse.SC_OK, result);
    }

    private void register(JSONObject body, HttpServletResponse resp) throws IOException, SQLException {
        try {
            userService.register(body.optString("name", null),
                                 body.optString("loginId", null),
                                 body.optString("password", null));
        } catch (UserService.ValidationException e) {
            JsonIO.error(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        JsonIO.write(resp, HttpServletResponse.SC_CREATED, new JSONObject().put("message", "Registered"));
    }
}