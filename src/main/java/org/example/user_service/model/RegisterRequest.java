package org.example.user_service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotNull(message = "Name can't be null")
        String name,
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,
        @Email(message = "Email must be valid")
        String email
)
{}
