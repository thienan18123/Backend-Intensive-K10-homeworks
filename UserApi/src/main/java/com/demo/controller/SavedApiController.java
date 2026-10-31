package com.demo.controller;


import com.demo.model.Article;
import com.demo.service.SavedListService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/api/saved/*")
public class SavedApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final transient SavedListService savedService = new SavedListService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int userId = userId(req);
        try {
            if (req.getPathInfo() == null || "/".equals(req.getPathInfo())) {     
                JSONArray result = new JSONArray();
                for (Article a : savedService.getSaved(userId)) {
                    result.put(new JSONObject()
                            .put("id", a.getId())
                            .put("title", a.getTitle())
                            .put("summary", a.getSummary()));
                }
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write(result.toString());
                return;
            }
            Integer articleId = articleId(req);                                  
            if (articleId == null) {
                JsonIO.error(resp, HttpServletResponse.SC_NOT_FOUND, "Not found");
                return;
            }
            JsonIO.write(resp, HttpServletResponse.SC_OK,
                    new JSONObject().put("saved", savedService.isSaved(userId, articleId)));
        } catch (SQLException e) {
            throw new ServletException("Cannot load saved list", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer articleId = articleId(req);
        try {
            if (articleId == null || !savedService.save(userId(req), articleId)) {
                JsonIO.error(resp, HttpServletResponse.SC_NOT_FOUND, "Article not found");
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot save article " + articleId, e);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer articleId = articleId(req);
        if (articleId == null) {
            JsonIO.error(resp, HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        try {
            savedService.remove(userId(req), articleId);
        } catch (SQLException e) {
            throw new ServletException("Cannot remove article " + articleId, e);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }


    private static int userId(HttpServletRequest req) {
        JSONObject claims = (JSONObject) req.getAttribute("jwtClaims");
        return Integer.parseInt(claims.getString("sub"));
    }

    
    private static Integer articleId(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.length() < 2) return null;
        try {
            return Integer.valueOf(path.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}