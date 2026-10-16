package com.demo.dao;

import com.demo.db.DbConnectionManager;
import com.demo.model.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {

    /** All articles that belong directly to one category, newest first. */
    public List<Article> findByCategory(int categoryId) throws SQLException {
        String sql = "SELECT id, category_id, title, summary, content FROM articles "
                   + "WHERE category_id = ? ORDER BY id DESC";
        List<Article> list = new ArrayList<>();
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            } 
        } catch (SQLException e) {
            throw new SQLException("Failed to load articles of category " + categoryId, e.getSQLState(), e);
        }
        return list;
    }

    /** One article, or null if the id doesn't exist. */
    public Article findById(int id) throws SQLException {
        String sql = "SELECT id, category_id, title, summary, content FROM articles WHERE id = ?";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to load article " + id, e.getSQLState(), e);
        }
    }

    private Article map(ResultSet rs) throws SQLException {
        return new Article(
                rs.getInt("id"),
                rs.getInt("category_id"),
                rs.getString("title"),
                rs.getString("summary"),
                rs.getString("content"));
    }
}