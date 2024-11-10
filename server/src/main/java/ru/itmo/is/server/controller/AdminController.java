package ru.itmo.is.server.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/admin")
public class AdminController {
    @GET
    public Response hello() {
        return Response.ok("Hello World!").build();
    }
}
