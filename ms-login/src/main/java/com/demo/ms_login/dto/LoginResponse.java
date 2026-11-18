package com.demo.ms_login.dto;

public record LoginResponse(String accessToken, String tokenType,
                            Long userId, String username, long expiresInSeconds) { }