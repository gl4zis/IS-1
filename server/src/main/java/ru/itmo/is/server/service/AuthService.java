package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.LoginRequest;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.dto.response.JwtResponse;
import ru.itmo.is.server.entity.security.AdminRegistrationBid;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.exception.ConflictException;
import ru.itmo.is.server.exception.UnauthorizedException;
import ru.itmo.is.server.mapper.UserMapper;
import ru.itmo.is.server.web.JwtManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserMapper mapper;
    @Inject
    private JwtManager jwtManager;

    @Transactional
    public Optional<JwtResponse> register(RegisterRequest req) {
        var user = mapper.toUser(req);
        if (isLoginBusy(user.getLogin()))
            throw new ConflictException("Login '" + user.getLogin() + "' is already in use");

        boolean isAdminsExist = em.createNamedQuery("User.isAdminsExist", Boolean.class)
                .getSingleResult();
        if (user.getRole() == Role.USER || !isAdminsExist) {
            em.persist(user);
            return Optional.of(new JwtResponse(jwtManager.createToken(user)));
        }

        em.persist(mapper.toBid(user));
        return Optional.empty();
    }

    public JwtResponse login(LoginRequest req) {
        var user = em.find(User.class, req.getLogin());
        if (user == null || !user.getPassword().equals(mapper.hash384(req.getPassword())))
            throw new UnauthorizedException("Invalid login or password");

        return new JwtResponse(jwtManager.createToken(user));
    }

    public User getUser(String jwt) {
        var user = em.find(User.class, jwtManager.getLogin(jwt));
        if (user == null || !user.getRole().equals(jwtManager.getRole(jwt)))
            throw new UnauthorizedException("Invalid auth token");
        return user;
    }

    public List<String> getBids() {
        return em.createNamedQuery("AdminRegistrationBid.findAllLogins", String.class).getResultList();
    }

    @Transactional
    public void acceptBid(String login) {
        var bid = findBid(login);
        em.remove(bid);
        em.persist(mapper.toUser(bid));
    }

    @Transactional
    public void rejectBid(String login) {
        em.remove(findBid(login));
    }

    private boolean isLoginBusy(String login) {
        var user = em.find(User.class, login);
        var bid = em.find(AdminRegistrationBid.class, login);
        return bid != null || user != null;
    }

    private AdminRegistrationBid findBid(String login) {
        var bid = em.find(AdminRegistrationBid.class, login);
        if (bid == null) throw new NotFoundException();
        return bid;
    }
}
