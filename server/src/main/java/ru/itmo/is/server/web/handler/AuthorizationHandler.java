package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is.server.exception.AuthorizationException;

@Provider
public class AuthorizationHandler implements ExceptionMapper<AuthorizationException> {
    @Override
    public Response toResponse(AuthorizationException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(exception.getMessage())
                .build();
    }
}
