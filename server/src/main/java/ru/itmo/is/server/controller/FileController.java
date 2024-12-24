package ru.itmo.is.server.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;
import ru.itmo.is.server.dto.response.FilteredResponse;
import ru.itmo.is.server.service.FileService;

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileController {
    @Inject
    private FileService fileService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@QueryParam("filename") String fileName, MultipartFormDataInput input) {
        fileService.upload(fileName, input);
        return Response.ok().build();
    }

    @GET
    @Path("download/{key}")
    public Response downloadFile(@PathParam("key") String key) {
        var fileO = fileService.getFile(key);
        if (fileO.isPresent()) {
            var file = fileO.get();
            Response.ResponseBuilder response = Response.ok(file.file());
            response.header("Content-Disposition", "attachment; filename=\"" + file.name() + "\"");
            return response.build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/history")
    public Response getImportHistory(@NotNull @Valid FilteredRequest.FilteredImportRequest request) {
        var history = fileService.getFileImports(request);
        var count = fileService.getHistoryCount();
        return Response.ok(new FilteredResponse<>(history, count)).build();
    }
}
