package ru.itmo.is.server.web.handler;

import jakarta.transaction.RollbackException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RollbackExceptionHandler implements ExceptionMapper<RollbackException> {

    @Override
    public Response toResponse(RollbackException exception) {
        if (exception.getCause() instanceof WebApplicationException webE) {
            return WebExceptionHandler.convert(webE);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(exception.getMessage())
                .build();
    }
}
