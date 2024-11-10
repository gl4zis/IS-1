package ru.itmo.is.server.web;

import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.exception.UnauthorizedException;
import ru.itmo.is.server.service.AuthService;

@Provider
@Log4j2
public class Interceptor implements ContainerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_TYPE = "Bearer";

    @Inject
    private ActiveUserHolder userHolder;
    @Inject
    private AuthService authService;

    @Override
    public void filter(ContainerRequestContext context) {
        var path = context.getUriInfo().getPath();
        var method = context.getMethod();
        log.info("Accepted {} {}", method, path);

        if (!path.startsWith("/auth")) {
            authIntercept(context);
            if (path.startsWith("/admin") && userHolder.getRole() != Role.ADMIN)
                throw new ForbiddenException("Permission denied");
        }
    }

    private void authIntercept(ContainerRequestContext context) {
        var authHeader = context.getHeaderString(AUTH_HEADER);
        if (authHeader == null) throw new UnauthorizedException("No authorization header");
        if (!authHeader.startsWith(AUTH_TYPE)) throw new UnauthorizedException("Invalid authorization type");
        var token = authHeader.substring(AUTH_TYPE.length() + 1);
        var user = authService.getUser(token);
        userHolder.setUser(user);
    }
}
