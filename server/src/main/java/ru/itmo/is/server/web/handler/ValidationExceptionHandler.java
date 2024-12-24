package ru.itmo.is.server.web.handler;

import jakarta.transaction.RollbackException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler  implements ExceptionMapper<RollbackException> {
    @Override
    public Response toResponse(RollbackException e) {
        throw new BadRequestException(e.getMessage(), e);
    }
}
