package ru.itmo.is.server.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.exception.InvalidRequestException;
import ru.itmo.is.server.exception.LoginIsBusyException;
import ru.itmo.is.server.service.AuthService;

@Path("/auth")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    public Response register(RegisterRequest req) {
        return Response.status(authService.register(req)).build();
    }
}
