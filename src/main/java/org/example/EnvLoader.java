package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvLoader {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env.local") // name of your env file
                .load();

        System.setProperty("CLOUDFLARE_R2_ACCESS_KEY_ID", dotenv.get("CLOUDFLARE_R2_ACCESS_KEY_ID"));
        System.setProperty("CLOUDFLARE_R2_SECRET_KEY", dotenv.get("CLOUDFLARE_R2_SECRET_KEY"));
        System.setProperty("CLOUDFLARE_R2_ENDPOINT_URL", dotenv.get("CLOUDFLARE_R2_ENDPOINT_URL"));
    }
}
