package com.demo.dao;

import com.demo.db.DbConnectionManager;
import com.demo.model.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SavedArticleDao {

    
    public void save(int userId, int articleId) throws SQLException {
        String sql = "INSERT INTO saved_articles (user_id, article_id) VALUES (?, ?) "
                   + "ON CONFLICT (user_id, article_id) DO NOTHING";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            ps.executeUpdate();
        }
    }

   
    public void remove(int userId, int articleId) throws SQLException {
        String sql = "DELETE FROM saved_articles WHERE user_id = ? AND article_id = ?";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            ps.executeUpdate();
        }
    }

   
    public boolean isSaved(int userId, int articleId) throws SQLException {
        String sql = "SELECT 1 FROM saved_articles WHERE user_id = ? AND article_id = ?";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

   
    public List<Article> findByUser(int userId) throws SQLException {
        String sql = "SELECT a.id, a.category_id, a.title, a.summary "
                   + "FROM saved_articles s "
                   + "JOIN articles a   ON a.id = s.article_id "
                   + "JOIN categories c ON c.id = a.category_id AND c.is_visible = TRUE "
                   + "WHERE s.user_id = ? "
                   + "ORDER BY s.saved_at DESC";
        List<Article> list = new ArrayList<>();
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Article(rs.getInt("id"), rs.getInt("category_id"),
                                         rs.getString("title"), rs.getString("summary"), null));
                }
            }
        }
        return list;
    }
}