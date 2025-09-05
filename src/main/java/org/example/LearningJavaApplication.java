
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@SpringBootApplication
@RestController
@EnableCaching
public class LearningJavaApplication {

    public static void main(String[] args) {
        // Dotenv dotenv = Dotenv.configure()
        //         .filename(".env.local")
        //         .ignoreIfMissing()
        //         .load();

        // System.setProperty("CLOUDFLARE_R2_ACCESS_KEY_ID", dotenv.get("CLOUDFLARE_R2_ACCESS_KEY_ID"));
        // System.setProperty("CLOUDFLARE_R2_SECRET_ACCESS_KEY", dotenv.get("CLOUDFLARE_R2_SECRET_ACCESS_KEY"));
        // System.setProperty("CLOUDFLARE_R2_ENDPOINT_URL", dotenv.get("CLOUDFLARE_R2_ENDPOINT_URL"));
        // System.setProperty("CLOUDINARY_URL", dotenv.get("CLOUDINARY_URL"));

        // System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        // System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        // System.setProperty("GITHUB_CLIENT_ID", dotenv.get("GITHUB_CLIENT_ID"));
        // System.setProperty("GITHUB_CLIENT_SECRET", dotenv.get("GITHUB_CLIENT_SECRET"));
        SpringApplication.run(LearningJavaApplication.class, args);
    }
}
