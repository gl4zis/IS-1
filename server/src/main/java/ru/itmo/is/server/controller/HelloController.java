package ru.itmo.is.server.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/hello")
public class HelloController {
    @GET
    public Response hello() {
        return Response.ok("Hello World!").build();
    }
}
