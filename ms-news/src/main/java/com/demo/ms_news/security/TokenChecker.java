package com.demo.ms_news.security;

import com.demo.ms_login.security.VerifiedUser;
import java.util.Optional;

public interface TokenChecker {
    Optional<VerifiedUser> check(String rawToken);
}