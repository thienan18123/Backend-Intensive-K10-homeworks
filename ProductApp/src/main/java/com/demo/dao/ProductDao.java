package com.demo.dao;

import com.demo.db.DbConnectionManager;
import com.demo.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getString("id"),      
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")));
            }
        }
        return products;
    }
}