package com.demo.ms_news.security;

import com.demo.ms_login.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.auth.mode", havingValue = "lib", matchIfMissing = true)
@RequiredArgsConstructor
public class LocalTokenChecker implements TokenChecker {

    private final JwtVerifier verifier;

    @Override
    public Optional<VerifiedUser> check(String rawToken) {
        return verifier.verify(rawToken);
    }
}