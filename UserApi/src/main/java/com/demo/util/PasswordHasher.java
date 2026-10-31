package com.demo.util;

import org.mindrot.jbcrypt.BCrypt;


public final class PasswordHasher {
   
    private static final int COST = 12;

    
    public static final int MAX_PASSWORD_BYTES = 72;

    private PasswordHasher() { }

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));   
    }

    public static boolean verify(String password, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2")) {
            return false;                                       
        }
        try {
            return BCrypt.checkpw(password, storedHash);        
        } catch (IllegalArgumentException e) {
            return false;                                       
        }
    }
}