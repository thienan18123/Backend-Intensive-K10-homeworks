package com.demo.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;


@WebServlet("/api/me")
public class MeApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        JSONObject claims = (JSONObject) req.getAttribute("jwtClaims");
        JSONObject me = new JSONObject()
                .put("id", Integer.parseInt(claims.getString("sub")))
                .put("loginId", claims.getString("loginId"))
                .put("name", claims.getString("name"));
        JsonIO.write(resp, HttpServletResponse.SC_OK, me);
    }
}