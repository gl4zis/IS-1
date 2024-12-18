package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebExceptionHandler implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        return convert(e);
    }

    public static Response convert(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(e.getMessage())
                .build();
    }
}
