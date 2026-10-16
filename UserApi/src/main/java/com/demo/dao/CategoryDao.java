package com.demo.dao;

import com.demo.db.DbConnectionManager;
import com.demo.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    
    public List<Category> findRoots() throws SQLException {
        String sql = "SELECT id, name, parent_id FROM categories WHERE parent_id IS NULL ORDER BY id";
        List<Category> list = new ArrayList<>();
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
        	throw new SQLException("Failed to load top-level categories", e.getSQLState(), e);
        }
        return list;
    }

   
    public List<Category> findChildren(int parentId) throws SQLException {
        String sql = "SELECT id, name, parent_id FROM categories WHERE parent_id = ? ORDER BY id";
        List<Category> list = new ArrayList<>();
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            } catch (SQLException e) {
            	throw new SQLException("Failed to load sub-level categories", e.getSQLState(), e);
            }
        }
        return list;
    }

    /** One category, or null if the id doesn't exist. */
    public Category findById(int id) throws SQLException {
        String sql = "SELECT id, name, parent_id FROM categories WHERE id = ?";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
        	throw new SQLException("Failed to load category", e.getSQLState(), e);
        }
    }

    private Category map(ResultSet rs) throws SQLException {
        int parent = rs.getInt("parent_id");
        Integer parentId = rs.wasNull() ? null : parent;   // SQL NULL -> Java null
        return new Category(rs.getInt("id"), rs.getString("name"), parentId);
    }
}