package com.demo.model;

import java.io.Serializable;


public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String loginId;
    private final String name;
    private final transient String passwordHash;   

    public User(int id, String loginId, String name, String passwordHash) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public int getId()              { return id; }
    public String getLoginId()      { return loginId; }
    public String getName()         { return name; }
    public String getPasswordHash() { return passwordHash; }

   
    public User withoutPassword() {
        return new User(id, loginId, name, null);
    }
}