package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ValidationException;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        return WebExceptionHandler.convert(new BadRequestException(e.getMessage(), e));
    }
}
