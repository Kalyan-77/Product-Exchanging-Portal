package com.project.Product.Exchanging.Portal.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode("password123");
        System.out.println("Hashed password: " + hashed);
    }
}
