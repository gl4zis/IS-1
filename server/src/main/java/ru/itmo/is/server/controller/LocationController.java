package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.request.filter.FilteredRequest.FilteredLocationRequest;
import ru.itmo.is.server.dto.response.FilteredResponse;
import ru.itmo.is.server.service.LocationService;

@Path("/location")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LocationController {
    @Inject
    private LocationService locationService;

    @POST
    @Path("/filtered")
    public Response getFilteredCoords(@NotNull @Valid FilteredLocationRequest req) {
        var locations = locationService.getFiltered(req);
        var count = locationService.countFiltered(req.getFilters());
        return Response.ok(new FilteredResponse<>(locations, count)).build();
    }

    @POST
    public Response createLocation(LocationRequest req) {
        locationService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateLocation(@PathParam("id") @NotNull Integer id, @Valid LocationRequest req) {
        locationService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@PathParam("id") @NotNull Integer id) {
        locationService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/linked-people")
    public Response getLinkedPeople(@PathParam("id") @NotNull Integer id) {
        return Response.ok(locationService.getLinkedPeople(id)).build();
    }

    @GET
    @Path("/select")
    public Response getForSelect() {
        return Response.ok(locationService.getForSelect()).build();
    }

    @GET
    @Path("/check-name-unique")
    public Response checkNameUnique(@QueryParam("name") String name) {
        return Response.ok(locationService.isNameUnique(name)).build();
    }
}
