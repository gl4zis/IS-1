package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

/**
 * Marker for handler. All exception extend this
 */
public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException() {
        super();
    }

    public abstract Response.Status getStatus();
}
