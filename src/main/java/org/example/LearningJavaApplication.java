
package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
@EnableCaching
public class LearningJavaApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env.local")
                .ignoreIfMissing()
                .load();

        System.setProperty("CLOUDFLARE_R2_ACCESS_KEY_ID", dotenv.get("CLOUDFLARE_R2_ACCESS_KEY_ID"));
        System.setProperty("CLOUDFLARE_R2_SECRET_ACCESS_KEY", dotenv.get("CLOUDFLARE_R2_SECRET_ACCESS_KEY"));
        System.setProperty("CLOUDFLARE_R2_ENDPOINT_URL", dotenv.get("CLOUDFLARE_R2_ENDPOINT_URL"));
        SpringApplication.run(LearningJavaApplication.class, args);
    }
}
