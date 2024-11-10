package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.service.PersonService;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {
    @Inject
    private PersonService personService;

    @GET
    public Response getAllPeople() {
        return Response.ok(personService.getAll()).build();
    }

    @POST
    public Response createPerson(PersonRequest req) {
        personService.create(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") int id) {
        return Response.ok(personService.get(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Integer id, PersonRequest req) {
        personService.update(id, req);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Integer id) {
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
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (weight !=null) return Response.ok(personService.countPeopleByWeight(weight)).build();
        else return Response.ok(personService.countPeopleByEyeColor(eyeColor)).build();
    }

    @GET
    @Path("/proportion")
    public Response getProportion(@QueryParam("eyeColor") Color eyeColor) {
        return Response.ok(personService.getPeopleProportionByEyeColor(eyeColor)).build();
    }
}
