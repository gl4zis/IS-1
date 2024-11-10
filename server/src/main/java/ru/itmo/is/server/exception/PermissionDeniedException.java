package ru.itmo.is.server.exception;

import jakarta.ws.rs.core.Response;

public class PermissionDeniedException extends CustomException {
    public PermissionDeniedException() {
        super("Permission denied");
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.FORBIDDEN;
    }
}
