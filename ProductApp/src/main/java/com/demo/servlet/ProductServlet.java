package com.demo.servlet;

import com.demo.dao.ProductDao;
import com.demo.model.Product;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.print("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Product list</title>");
        out.print("<style>");
        out.print("body{font-family:Arial,sans-serif;margin:40px;color:#222}");
        out.print("h1{color:#1a56db}");
        out.print("table{border-collapse:collapse;min-width:500px}");
        out.print("th,td{border:1px solid #ddd;padding:10px 14px;text-align:left}");
        out.print("th{background:#1a56db;color:#fff}");
        out.print("tr:nth-child(even){background:#f5f7fb}");
        out.print(".num{text-align:right}.error{color:#b91c1c}");
        out.print("</style></head><body>");
        out.print("<h1>Product list</h1>");

        try {
            List<Product> products = new ProductDao().getAllProducts();

            if (products.isEmpty()) {
                out.print("<p>No products found.</p>");
            } else {
                out.print("<p>Total: " + products.size() + " products</p>");
                out.print("<table><tr><th>#</th><th>Name</th><th class='num'>Price ($)</th><th class='num'>Stock</th></tr>");
                int i = 1;
                for (Product p : products) {
                    out.print("<tr>");
                    out.print("<td>" + i++ + "</td>");
                    out.print("<td>" + esc(p.getName()) + "</td>");
                    out.print("<td class='num'>" + String.format("%,.2f", p.getPrice()) + "</td>");
                    out.print("<td class='num'>" + p.getStock() + "</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("<p class='error'>Could not load products: " + esc(String.valueOf(e.getMessage())) + "</p>");
        }

        out.print("</body></html>");
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}