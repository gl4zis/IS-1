package ru.itmo.is.server.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class UnauthorizedException extends ClientErrorException {
    public UnauthorizedException(String message) {
        super(message, Response.Status.UNAUTHORIZED);
    }
}
