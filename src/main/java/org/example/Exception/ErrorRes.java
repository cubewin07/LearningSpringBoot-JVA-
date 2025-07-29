package org.example.Exception;

public record ErrorRes(int status, Object message, long timestamp) {
    public ErrorRes {
    }
}
