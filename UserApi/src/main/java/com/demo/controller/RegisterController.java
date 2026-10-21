package com.demo.controller;

import com.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final transient UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");                   
        String name = req.getParameter("name");
        String loginId = req.getParameter("loginId");
        String password = req.getParameter("password");

        try {
            userService.register(name, loginId, password);
        } catch (UserService.ValidationException e) {
            req.setAttribute("error", e.getMessage());        
            req.setAttribute("name", name);                   
            req.setAttribute("loginId", loginId);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        } catch (SQLException e) {
            throw new ServletException("Cannot register user", e);
        }
        resp.sendRedirect(req.getContextPath() + "/login?registered=1");
    }
}