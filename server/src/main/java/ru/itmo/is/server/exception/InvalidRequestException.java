package ru.itmo.is.server.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super();
    }

    public InvalidRequestException(String message) {
        super("Invalid request: " + message);
    }
}
