package com.demo.controller;

import com.demo.model.Category;
import com.demo.model.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));    
        try {
            Category category = (id == null) ? null : newsService.getCategory(id);
            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
         
            Category parent = (category.getParentId() == null) ? null
                    : newsService.getCategory(category.getParentId());

            req.setAttribute("category", category);
            req.setAttribute("parent", parent);
            req.setAttribute("subCategories", newsService.getSubCategories(id));
            req.setAttribute("articles", newsService.getArticles(id));
        } catch (SQLException e) {
            throw new ServletException("Cannot load category " + id, e);
        }
        req.getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
    }

  
    private static Integer parseId(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}