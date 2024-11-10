package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is.server.exception.PermissionDeniedException;

@Provider
public class PermissionDeniedHandler implements ExceptionMapper<PermissionDeniedException> {
    @Override
    public Response toResponse(PermissionDeniedException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(exception.getMessage())
                .build();
    }
}
