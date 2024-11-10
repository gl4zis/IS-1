package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is.server.dto.request.PersonRequest;
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
}
