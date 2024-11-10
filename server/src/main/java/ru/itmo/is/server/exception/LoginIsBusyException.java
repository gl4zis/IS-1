package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

public class LoginIsBusyException extends CustomException {
    public LoginIsBusyException(String login) {
        super("Login '" + login + "' is busy");
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.CONFLICT;
    }
}
