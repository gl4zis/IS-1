package ru.itmo.is.server.web;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is.server.exception.CustomException;

@Provider
public class ExceptionHandler implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException exception) {
        return Response.status(exception.getStatus())
                .entity(exception.getMessage())
                .build();
    }
}
