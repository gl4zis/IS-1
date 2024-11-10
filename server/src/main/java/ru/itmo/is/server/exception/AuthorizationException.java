package ru.itmo.is.server.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Invalid auth token");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
