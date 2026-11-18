package com.demo.ms_login.service;

import com.demo.ms_login.dto.*;
import com.demo.ms_login.entity.User;
import com.demo.ms_login.repository.UserRepository;
import com.demo.ms_login.security.JwtIssuer;
import com.demo.ms_login.security.JwtVerifier;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtIssuer issuer;
    private final JwtVerifier verifier;

    public LoginResponse login(LoginRequest req) {
        User u = users.findByUsername(req.username())
            .orElseThrow(() -> new BadCredentialsException());
        if (!encoder.matches(req.password(), u.getPassword())) {
            throw new BadCredentialsException();
        }
        String token = issuer.issue(u.getId(), u.getUsername());
        return new LoginResponse(token, "Bearer", u.getId(), u.getUsername(), 3600);
    }

    public ValidateResponse validate(String token) {
        return verifier.verify(token)
            .map(u -> new ValidateResponse(true, u.userId(), u.username()))
            .orElse(new ValidateResponse(false, null, null));
    }
}