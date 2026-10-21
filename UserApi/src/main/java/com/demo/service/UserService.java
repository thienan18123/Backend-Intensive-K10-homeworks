package com.demo.service;

import com.demo.dao.UserDao;
import com.demo.model.User;
import com.demo.util.PasswordHasher;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserService {
    private static final Pattern LOGIN_ID = Pattern.compile("[A-Za-z0-9_]{3,50}"); //correct format
    private static final String UNIQUE_VIOLATION = "23505";   //posgre error code

    private final UserDao userDao = new UserDao();

    public static class ValidationException extends Exception {
        private static final long serialVersionUID = 1L;
        public ValidationException(String message) { super(message); }
    }

    public void register(String name, String loginId, String password)
            throws ValidationException, SQLException {
        name = (name == null) ? "" : name.trim();
        loginId = (loginId == null) ? "" : loginId.trim();
        password = (password == null) ? "" : password;

        if (name.isEmpty() || name.length() > 100) {
            throw new ValidationException("Name is required (max 100 characters).");
        }
        if (!LOGIN_ID.matcher(loginId).matches()) {
            throw new ValidationException("ID must be 3-50 letters, numbers or underscores.");
        }
        if (password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters.");
        }

        try {
            userDao.insert(loginId, name, PasswordHasher.hash(password));
        } catch (SQLException e) {
            if (UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new ValidationException("This ID is already taken.");
            }
            throw e;
        }
    }

    
    public User login(String loginId, String password) throws SQLException {
        if (loginId == null || password == null) {
            return null;
        }
        User user = userDao.findByLoginId(loginId.trim());
        if (user == null || !PasswordHasher.verify(password, user.getPasswordHash())) {
            return null;                              
        }
        return user.withoutPassword();
    }
}