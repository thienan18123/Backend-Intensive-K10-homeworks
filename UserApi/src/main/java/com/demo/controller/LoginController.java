package com.demo.controller;

import com.demo.model.User;
import com.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final  UserService userService = new UserService();

 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String loginId = req.getParameter("loginId");
        String password = req.getParameter("password");

        User user;
        try {
            user = userService.login(loginId, password);
        } catch (SQLException e) {
            throw new ServletException("Cannot log in", e);
        }

        if (user == null) {                                   
            req.setAttribute("error", "Wrong ID or password.");
            req.setAttribute("loginId", loginId);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();               
        req.changeSessionId();                                
        session.setAttribute("user", user);                   
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}