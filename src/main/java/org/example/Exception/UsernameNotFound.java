package org.example.Exception;

public class UsernameNotFound extends RuntimeException {
    public UsernameNotFound(String message) {
        super(message);
    }
}
