package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.request.filter.FilteredRequest.FilteredCoordRequest;
import ru.itmo.is.server.dto.response.FilteredResponse;
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
    @Path("/filtered")
    public Response getFilteredCoords(@NotNull @Valid FilteredCoordRequest req) {
        var coords = coordService.getFiltered(req);
        var count = coordService.countFiltered(req.getFilters());
        return Response.ok(new FilteredResponse<>(coords, count)).build();
    }

    @POST
    public Response createCoord(@Valid CoordRequest req) {
        coordService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getCoord(@PathParam("id") @NotNull Integer id) {
        return Response.ok(coordService.get(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoord(@PathParam("id") @NotNull Integer id, @Valid CoordRequest req) {
        coordService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoord(@PathParam("id") @NotNull Integer id) {
        coordService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/linked-people")
    public Response getLinkedPeople(@PathParam("id") @NotNull Integer id, @QueryParam("enriched") boolean enriched) {
        return enriched ? Response.ok(coordService.getLinkedPeople(id)).build() :
                Response.ok(coordService.getLinkedPeopleIds(id)).build();
    }
}
