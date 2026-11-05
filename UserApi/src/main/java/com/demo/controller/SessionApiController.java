package com.demo.controller;

import com.demo.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/api/sessions")
public class SessionApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final transient RefreshTokenService refreshService = new RefreshTokenService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        JSONObject claims = (JSONObject) req.getAttribute("jwtClaims");
        try {
            refreshService.revokeAll(Integer.parseInt(claims.getString("sub")));
        } catch (SQLException e) {
            throw new ServletException("Cannot log out everywhere", e);
        }
        RefreshCookieController.clear(req, resp);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}