package org.example.Exception;

public class TooManyRequest extends RuntimeException {
    public TooManyRequest(String message) {
        super(message);
    }
}
