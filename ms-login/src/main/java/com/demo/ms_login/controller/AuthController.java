package com.demo.ms_login.controller;

import com.demo.ms_login.dto.*;
import com.demo.ms_login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return auth.login(req);
    }

    @PostMapping("/validate")                
    public ValidateResponse validate(@RequestBody ValidateRequest req) {
        return auth.validate(req.token());
    }
}