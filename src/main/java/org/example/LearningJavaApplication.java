
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
public class LearningJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningJavaApplication.class, args);
    }
}
