package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

public class AuthorizationException extends CustomException {
    public AuthorizationException() {
        super("Invalid auth token");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.UNAUTHORIZED;
    }
}
