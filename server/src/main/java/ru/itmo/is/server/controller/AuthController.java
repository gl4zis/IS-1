package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.LoginRequest;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.service.AuthService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest req) {
        var res = authService.register(req);
        if (res.isPresent()) {
            return Response.status(Response.Status.CREATED)
                    .entity(res.get())
                    .build();
        }

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest req) {
        return Response.ok(authService.login(req)).build();
    }

    // Just checking if token is valid in interceptor
    @GET
    @Path("/check")
    public Response authCheck() {
        return Response.ok().build();
    }
}
