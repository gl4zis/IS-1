package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.request.filter.FilteredRequest.FilteredPersonRequest;
import ru.itmo.is.server.dto.response.FilteredResponse;
import ru.itmo.is.server.dto.response.IdResponse;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.service.PersonService;
import ru.itmo.is.server.validation.entity.PersonValidator;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {
    @Inject
    private PersonService personService;
    @Inject
    private PersonValidator personValidator;

    @POST
    @Path("/filtered")
    public Response getFilteredCoords(@NotNull @Valid FilteredPersonRequest req) {
        var people = personService.getFiltered(req);
        var count = personService.countFiltered(req.getFilters());
        return Response.ok(new FilteredResponse<>(people, count)).build();
    }

    @POST
    public Response createPerson(@Valid PersonRequest req) {
        return Response
                .status(Response.Status.CREATED)
                .entity(new IdResponse(personService.create(req))
                ).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") @NotNull Integer id, @Valid PersonRequest req) {
        personService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") @NotNull Integer id) {
        personService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/height-sum")
    public Response getHeightSum() {
        return Response.ok(personService.getAllHeightSum()).build();
    }

    @GET
    @Path("/max-by-coords")
    public Response getMaxByCoords() {
        return Response.ok(personService.getPersonWithMaxCoords()).build();
    }

    @GET
    @Path("/count")
    public Response countPeople(@QueryParam("weight") Long weight, @QueryParam("eyeColor") Color eyeColor) {
        if ((weight == null && eyeColor == null) || (weight != null && eyeColor != null)) {
            throw new BadRequestException("Request should have one of 'weight' and 'eyeColor' params");
        }
        if (weight != null) return Response.ok(personService.countPeopleByWeight(weight)).build();
        else return Response.ok(personService.countPeopleByEyeColor(eyeColor)).build();
    }

    @GET
    @Path("/proportion")
    public Response getProportion(@QueryParam("eyeColor") @NotNull Color eyeColor) {
        return Response.ok(personService.getPeopleProportionByEyeColor(eyeColor)).build();
    }

    @GET
    @Path("/check-name-unique")
    public Response checkNameUnique(@QueryParam("name") String name) {
        return Response.ok(personValidator.isNameUnique(name)).build();
    }

    @GET
    @Path("/check-passport-unique")
    public Response checkPassportUnique(@QueryParam("passportId") String passportId) {
        return Response.ok(personValidator.isPassportUnique(passportId)).build();
    }
}
