package com.demo.controller;

import com.demo.model.User;
import com.demo.service.RefreshTokenService;
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
    private final transient RefreshTokenService refreshService = new RefreshTokenService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();
        try {
          
            if ("/refresh".equals(action)) {
                refresh(req, resp);
                return;
            }
            if ("/logout".equals(action)) {
                logout(req, resp);
                return;
            }

            JSONObject body = JsonIO.readBody(req);
            if (body == null) {
                JsonIO.error(resp, HttpServletResponse.SC_BAD_REQUEST, "Body must be valid JSON");
                return;
            }
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
        RefreshCookieController.set(req, resp, refreshService.issue(user.getId()));   
        sendAccessToken(user, req, resp);                                    
    }

    private void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String token = RefreshCookieController.read(req);
        RefreshTokenService.Rotation rotation = (token == null) ? null : refreshService.rotate(token);
        User user = (rotation == null) ? null : userService.findById(rotation.userId());
        if (user == null) {
        	RefreshCookieController.clear(req, resp);
          
            JsonIO.error(resp, HttpServletResponse.SC_UNAUTHORIZED, "Please log in again");
            return;
        }
        if (rotation.newToken() != null) {
        	RefreshCookieController.set(req, resp, rotation.newToken());             
        }
        sendAccessToken(user, req, resp);
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        String token = RefreshCookieController.read(req);
        if (token != null) {
            refreshService.revoke(token);                                    
        }
        RefreshCookieController.clear(req, resp);
      
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /** New access token (5 minutes) bound to a new fingerprint cookie. */
    private void sendAccessToken(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject result = new JSONObject()
                .put("token", JwtUtil.createToken(user))
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