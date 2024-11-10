package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.service.CoordService;

@Path("/coord")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoordController {
    @Inject
    private CoordService coordService;

    @GET
    public Response getAllCoords() {
        return Response.ok(coordService.getAll()).build();
    }

    @POST
    public Response createCoord(CoordRequest req) {
        coordService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getCoord(@PathParam("id") int id) {
        return Response.ok(coordService.get(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoord(@PathParam("id") Integer id, CoordRequest req) {
        coordService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoord(@PathParam("id") Integer id) {
        coordService.delete(id);
        return Response.ok().build();
    }
}
