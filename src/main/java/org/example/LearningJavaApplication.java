package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearningJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningJavaApplication.class, args);
    }

    @Bean
    CommandLineRunner runner (StudentRepository studentRepository) {
        return args -> {
            Student student = new Student("John", "Doe", "john@gmail.com", 25);
            studentRepository.save(student);
        };
    }
}
