package com.demo.controller;


import com.demo.model.ArticlePage;
import com.demo.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/article")
public class ArticleController extends HttpServlet {
    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	Integer id = parseId(req.getParameter("id"));     
    	try {
    	    ArticlePage page = (id == null) ? null : newsService.getArticlePage(id);
    	    if (page == null) {
    	        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    	        return;
    	    }
    	    req.setAttribute("article", page.getArticle());
    	    req.setAttribute("category", page.getCategory());
    	} catch (SQLException e) {
    	    throw new ServletException("Cannot load article " + id, e);
    	}
    	req.getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
    }

    private static Integer parseId(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}