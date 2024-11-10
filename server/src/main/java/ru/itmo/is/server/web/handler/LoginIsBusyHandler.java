package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is.server.exception.LoginIsBusyException;

@Provider
public class LoginIsBusyHandler implements ExceptionMapper<LoginIsBusyException> {

    @Override
    public Response toResponse(LoginIsBusyException exception) {
        return Response.status(Response.Status.CONFLICT).entity(exception.getMessage()).build();
    }
}
