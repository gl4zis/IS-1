package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

public class InvalidRequestException extends CustomException {
    public InvalidRequestException() {
        super();
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
