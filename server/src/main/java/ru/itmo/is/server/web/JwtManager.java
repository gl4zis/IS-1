package ru.itmo.is.server.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is.server.config.AppProps;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.exception.UnauthorizedException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class JwtManager {
    private static final String ROLE_CLAIM_KEY = "role";

    @Inject
    private AppProps props;
    private SecretKey accessKey;

    @PostConstruct
    public void init() {
        accessKey = Keys.hmacShaKeyFor(props.getAccessKey().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(User user) {
        var now = new Date();
        var exp = Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(user.getLogin())
                .claim(ROLE_CLAIM_KEY, user.getRole())
                .issuedAt(now)
                .notBefore(now)
                .expiration(exp)
                .signWith(accessKey)
                .compact();
    }

    public String getLogin(String jwt) {
        return getClaims(jwt).getSubject();
    }

    public Role getRole(String jwt) {
        return Role.valueOf(getClaims(jwt).get(ROLE_CLAIM_KEY, String.class));
    }

    private Claims getClaims(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(accessKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid auth token");
        }
    }
}
