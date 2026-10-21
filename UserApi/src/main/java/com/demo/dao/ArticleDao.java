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

	
	public List<Article> findForRootCategories(Connection c) throws SQLException {
	    String sql = "SELECT id, category_id, title, summary, content FROM articles "
	               + "WHERE category_id IN (SELECT id FROM categories "
	               + "                      WHERE parent_id IS NULL AND is_visible = TRUE) ";
	    List<Article> list = new ArrayList<>();
	    try (PreparedStatement ps = c.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            list.add(map(rs));
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Failed to load articles of top-level categories", e.getSQLState(), e);
	    }
	    return list;
	}
	
    public List<Article> findByCategory(Connection c,int categoryId) throws SQLException {
        String sql = "SELECT id, category_id, title, summary, content FROM articles "
                   + "WHERE category_id = ? ";
        List<Article> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
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

  
    public Article findById(Connection c ,int id) throws SQLException {
        String sql = "SELECT id, category_id, title, summary, content FROM articles WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
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