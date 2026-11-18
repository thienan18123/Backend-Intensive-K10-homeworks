package com.demo.ms_login.dto;

public record ValidateResponse(boolean valid, Long userId, String username) { }
