package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dao.UserDao;
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

@RequestScoped
public class AuthService {
    @Inject
    private UserDao userDao;
    @Inject
    private UserMapper mapper;
    @Inject
    private JwtManager jwtManager;

    @Transactional
    public Optional<JwtResponse> register(RegisterRequest req) {
        var user = mapper.toUser(req);
        if (userDao.isLoginBusy(user.getLogin()))
            throw new ConflictException("Login '" + user.getLogin() + "' is already in use");

        if (user.getRole() == Role.USER || userDao.getAdmins().isEmpty()) {
            userDao.save(user);
            return Optional.of(new JwtResponse(jwtManager.createToken(user)));
        }

        userDao.save(mapper.toBid(user));
        return Optional.empty();
    }

    public JwtResponse login(LoginRequest req) {
        var userO = userDao.getUser(req.getLogin());
        if (userO.isEmpty()) throw new UnauthorizedException("Invalid login or password");
        if (!userO.get().getPassword().equals(mapper.hash384(req.getPassword())))
            throw new UnauthorizedException("Invalid login or password");

        return new JwtResponse(jwtManager.createToken(userO.get()));
    }

    public User getUser(String jwt) {
        var login = jwtManager.getLogin(jwt);
        var role = jwtManager.getRole(jwt);

        var userO = userDao.getUser(login);
        if (userO.isEmpty()) throw new UnauthorizedException("Invalid auth token");
        if (!userO.get().getRole().equals(role)) throw new UnauthorizedException("Invalid auth token");
        return userO.get();
    }

    public List<String> getBids() {
        return userDao.getAdminBids().stream().map(AdminRegistrationBid::getLogin).toList();
    }

    @Transactional
    public void acceptBid(String login) {
        var bidO = userDao.getAdminBid(login);
        if (bidO.isEmpty()) throw new NotFoundException();

        userDao.deleteBid(login);
        userDao.save(new User(bidO.get().getLogin(), bidO.get().getPassword(), Role.ADMIN));
    }

    @Transactional
    public void rejectBid(String login) {
        var bidO = userDao.getAdminBid(login);
        if (bidO.isEmpty()) throw new NotFoundException();

        userDao.deleteBid(login);
    }
}
