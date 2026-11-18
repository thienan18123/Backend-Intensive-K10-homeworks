package com.demo.ms_login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MsLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLoginApplication.class, args);
    }

    
    @Bean
    CommandLineRunner printHash(PasswordEncoder encoder) {
        return args -> System.out.println(">>> HASH: " + encoder.encode("123456"));
    }
}