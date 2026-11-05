package com.demo.service;

import com.demo.dao.RefreshTokenDao;
import com.demo.db.DbConnectionManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HexFormat;

public class RefreshTokenService {
    public static final long TTL_SECONDS = 7L * 24 * 60 * 60;   // 7 days

    private static final long GRACE_SECONDS = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final RefreshTokenDao dao = new RefreshTokenDao();

   
    public record Rotation(int userId, String newToken) { }

  
    public String issue(int userId) throws SQLException {
        String token = randomHex(32);
        try (Connection c = DbConnectionManager.getConnection()) {
            dao.insert(c, userId, sha256(token), randomHex(16), TTL_SECONDS);
        }
        return token;
    }

   
    public Rotation rotate(String token) throws SQLException {
        try (Connection c = DbConnectionManager.getConnection()) {
            c.setAutoCommit(false);                           
            try {
                Rotation result = rotate(c, sha256(token));
                c.commit();
                return result;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);                           
            }
        }
    }

    private Rotation rotate(Connection c, String hash) throws SQLException {
        RefreshTokenDao.Row row = dao.findForUpdate(c, hash, GRACE_SECONDS);
        if (row == null || !row.active()) {
            return null;                                     
        }
        if (row.used()) {
            if (row.recentlyUsed()) {
                return new Rotation(row.userId(), null);        
            }
            dao.deleteFamily(c, row.familyId());                
            return null;
        }
        dao.markUsed(c, row.id());
        String newToken = randomHex(32);
        dao.insert(c, row.userId(), sha256(newToken), row.familyId(), TTL_SECONDS);
        return new Rotation(row.userId(), newToken);
    }


    public void revoke(String token) throws SQLException {
        dao.deleteFamilyOf(sha256(token));
    }

 
    public void revokeAll(int userId) throws SQLException {
        dao.deleteAllForUser(userId);
    }

    private static String randomHex(int bytes) {
        byte[] b = new byte[bytes];
        RANDOM.nextBytes(b);
        return HexFormat.of().formatHex(b);
    }

    private static String sha256(String value) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}