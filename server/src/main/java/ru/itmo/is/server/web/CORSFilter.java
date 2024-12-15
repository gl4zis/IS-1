package ru.itmo.is.server.web;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;


@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
        resp.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, X-Requested-With, Accept, Authorization");
        resp.getHeaders().add("Access-Control-Allow-Credentials", "true");

        if (req.getHeaderString("Origin").contains("localhost")) {
            resp.getHeaders().add("Access-Control-Allow-Origin", "*");
        }

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(Response.Status.OK.getStatusCode());
        }
    }
}
