package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.service.AuthService;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminController {
    @Inject
    private AuthService authService;

    @GET
    @Path("/bid")
    public Response getAllRegisterBids() {
        return Response.ok(authService.getBids()).build();
    }

    @GET
    @Path("/bid/accept")
    public Response acceptBid(@QueryParam("login") @NotNull String login) {
        authService.acceptBid(login);
        return Response.ok().build();
    }

    @DELETE
    @Path("/bid/reject")
    public Response rejectBid(@QueryParam("login") @NotNull String login) {
        authService.rejectBid(login);
        return Response.ok().build();
    }
}
