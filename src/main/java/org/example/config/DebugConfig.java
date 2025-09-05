package org.example.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugConfig {
    @Bean
    CommandLineRunner printEnv(Environment env) {
        return args -> {
            System.out.println("Resolved R2 access-key-id = " + env.getProperty("cloudflare.r2.access-key-id"));
            System.out.println("Resolved R2 secret-access-key = " + env.getProperty("cloudflare.r2.secret-access-key"));
            System.out.println("Resolved R2 endpoint-url = " + env.getProperty("cloudflare.r2.endpoint-url"));
        };
    }
}