package com.demo.controller;


import com.demo.model.CategoryPage;
import com.demo.service.NewsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = parseId(req.getParameter("id"));    
   
        try {
            CategoryPage page = (id == null) ? null : newsService.getCategoryPage(id);
            if (page == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("category", page.getCategory());
            req.setAttribute("parent", page.getParent());
            req.setAttribute("subCategories", page.getSubCategories());
            req.setAttribute("articles", page.getArticles());
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