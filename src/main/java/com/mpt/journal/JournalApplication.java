package com.mpt.journal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            System.out.println("Admin BCrypt hash: " + encoder.encode("admin123"));
            System.out.println("User BCrypt hash: " + encoder.encode("user123"));
        };
    }
}