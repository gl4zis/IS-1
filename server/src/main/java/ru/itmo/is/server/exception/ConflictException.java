package ru.itmo.is.server.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class ConflictException extends ClientErrorException {
    public ConflictException() {
        super(Response.Status.CONFLICT);
    }

    public ConflictException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
