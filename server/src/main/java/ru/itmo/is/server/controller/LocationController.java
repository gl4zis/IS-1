package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.request.filter.FilteredRequest.FilteredLocationRequest;
import ru.itmo.is.server.dto.response.filtered.LocationFilteredResponse;
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
    @Path("/filtered")
    public Response getFilteredCoords(@NotNull @Valid FilteredLocationRequest req) {
        var locations = locationService.getFiltered(req);
        var count = locationService.countFiltered(req.getFilters());
        return Response.ok(new LocationFilteredResponse(locations, count)).build();
    }

    @POST
    public Response createLocation(LocationRequest req) {
        locationService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getLocation(@PathParam("id") @NotNull Integer id) {
        return Response.ok(locationService.get(id)).build();
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
    public Response getLinkedPeople(@PathParam("id") @NotNull Integer id, @QueryParam("enriched") boolean enriched) {
        return enriched ? Response.ok(locationService.getLinkedPeople(id)).build() :
                Response.ok(locationService.getLinkedPeopleIds(id)).build();
    }
}
