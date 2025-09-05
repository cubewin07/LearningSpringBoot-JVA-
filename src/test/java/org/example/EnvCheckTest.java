package org.example;

import org.junit.jupiter.api.Test;

public class EnvCheckTest {
    @Test
    void printEnvVars() {
        System.out.println("CLOUDFLARE_R2_ACCESS_KEY_ID = " + System.getenv("CLOUDFLARE_R2_ACCESS_KEY_ID"));
        System.out.println("CLOUDFLARE_R2_SECRET_ACCESS_KEY = " + System.getenv("CLOUDFLARE_R2_SECRET_ACCESS_KEY"));
        System.out.println("CLOUDFLARE_R2_ENDPOINT_URL = " + System.getenv("CLOUDFLARE_R2_ENDPOINT_URL"));
        System.out.println("OAUTH_GITHUB_CLIENT_SECRET = " + System.getenv("OAUTH_GITHUB_CLIENT_SECRET"));
        System.out.println("SPRING_DATASOURCE_PASSWORD = " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
    }
}
