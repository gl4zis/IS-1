package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

public class SignInException extends CustomException {
    public SignInException() {
        super("Invalid login or password");
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.UNAUTHORIZED;
    }
}
