package org.example.authen;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public record RegisterRequest(String name, String password, String email) {
}
