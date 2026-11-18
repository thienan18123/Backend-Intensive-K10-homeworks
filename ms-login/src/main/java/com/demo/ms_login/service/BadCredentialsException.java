package com.demo.ms_login.service;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Invalid username or password");
    }
    
}
