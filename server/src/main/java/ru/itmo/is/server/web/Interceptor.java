package ru.itmo.is.server.web;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;

@Provider
@Log4j2
public class Interceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        log.info("REQUEST ON: {}", requestContext.getUriInfo().getPath());
    }
}
