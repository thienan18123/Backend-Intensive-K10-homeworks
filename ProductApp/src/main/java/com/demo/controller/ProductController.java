package com.demo.controller;

import com.demo.dao.ProductDao;
import com.demo.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
           
            List<Product> products = productDao.getAllProducts();

            
            req.setAttribute("products", products);

        } catch (Exception e) {
            req.setAttribute("error", "Could not load products: " + e.getMessage());
        }

        // view
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}