package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.service.LocationService;

@Path("/location")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LocationController {
    @Inject
    private LocationService locationService;

    @GET
    public Response getAllLocations() {
        return Response.ok(locationService.getAll()).build();
    }

    @POST
    public Response createLocation(LocationRequest req) {
        locationService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getLocation(@PathParam("id") int id) {
        return Response.ok(locationService.get(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateLocation(@PathParam("id") Integer id, LocationRequest req) {
        locationService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@PathParam("id") Integer id) {
        locationService.delete(id);
        return Response.ok().build();
    }
}
