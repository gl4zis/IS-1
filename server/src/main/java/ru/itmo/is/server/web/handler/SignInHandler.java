package ru.itmo.is.server.web.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.is.server.exception.SignInException;

@Provider
public class SignInHandler implements ExceptionMapper<SignInException> {
    @Override
    public Response toResponse(SignInException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(exception.getMessage()).build();
    }
}
