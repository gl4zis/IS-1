package ru.itmo.is.server.web;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(exception.getMessage())
                .build();
    }
}
