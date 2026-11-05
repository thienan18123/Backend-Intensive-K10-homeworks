package com.demo.dao;

import com.demo.db.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RefreshTokenDao {

   
    public record Row(int id, int userId, String familyId, boolean active, boolean used, boolean recentlyUsed) { }

    public void insert(Connection c, int userId, String tokenHash, String familyId, long ttlSeconds)
            throws SQLException {
        String sql = "INSERT INTO refresh_tokens (user_id, token_hash, family_id, expires_at) "
                   + "VALUES (?, ?, ?, now() + make_interval(secs => ?))";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, tokenHash);
            ps.setString(3, familyId);
            ps.setLong(4, ttlSeconds);
            ps.executeUpdate();
        }
    }


    public Row findForUpdate(Connection c, String tokenHash, long graceSeconds) throws SQLException {
        String sql = "SELECT id, user_id, family_id, "
                   + "       expires_at > now() AS active, "
                   + "       used_at IS NOT NULL AS used, "
                   + "       used_at > now() - make_interval(secs => ?) AS recently_used "
                   + "FROM refresh_tokens WHERE token_hash = ? FOR UPDATE";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, graceSeconds);
            ps.setString(2, tokenHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Row(rs.getInt("id"), rs.getInt("user_id"), rs.getString("family_id"),
                               rs.getBoolean("active"), rs.getBoolean("used"), rs.getBoolean("recently_used"));
            }
        }
    }

    public void markUsed(Connection c, int id) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("UPDATE refresh_tokens SET used_at = now() WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    public void deleteFamily(Connection c, String familyId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM refresh_tokens WHERE family_id = ?")) {
            ps.setString(1, familyId);
            ps.executeUpdate();
        }
    }

 
    public void deleteFamilyOf(String tokenHash) throws SQLException {
        String sql = "DELETE FROM refresh_tokens WHERE family_id = "
                   + "(SELECT family_id FROM refresh_tokens WHERE token_hash = ?)";
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tokenHash);
            ps.executeUpdate();
        }
    }

   
    public void deleteAllForUser(int userId) throws SQLException {
        try (Connection c = DbConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM refresh_tokens WHERE user_id = ?")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}