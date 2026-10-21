package com.demo.dao;

import com.demo.db.DbConnectionManager;
import com.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

   
    public User findByLoginId(String loginId) throws SQLException {
        String sql = "SELECT id, login_id, name, password_hash FROM users WHERE login_id = ?";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new User(rs.getInt("id"), rs.getString("login_id"),
                                rs.getString("name"), rs.getString("password_hash"));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to load user " + loginId, e.getSQLState(), e);
        }
    }

   
    public void insert(String loginId, String name, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (login_id, name, password_hash) VALUES (?, ?, ?)";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, name);
            ps.setString(3, passwordHash);
            ps.executeUpdate();                        
        } catch (SQLException e) {
            throw new SQLException("Failed to insert user " + loginId, e.getSQLState(), e);
        }
    }
}