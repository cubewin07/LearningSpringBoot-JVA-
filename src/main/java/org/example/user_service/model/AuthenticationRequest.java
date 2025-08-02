package org.example.user_service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotNull(message = "Email can't be null")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
)
{}
