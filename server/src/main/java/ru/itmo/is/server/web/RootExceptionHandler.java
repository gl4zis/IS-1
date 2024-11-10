package ru.itmo.is.server.web;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RootExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException webE) return convert(webE);
        var cause = e.getCause();
        while (cause != null) {
            System.out.println(cause.getMessage());
            if (cause instanceof WebApplicationException webE) return convert(webE);
            cause = cause.getCause();
        }
        throw new RuntimeException(e);
    }

    private Response convert(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(e.getMessage())
                .build();
    }
}
