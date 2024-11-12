package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.UserDao;
import ru.itmo.is.server.dto.request.LoginRequest;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.dto.response.JwtResponse;
import ru.itmo.is.server.entity.security.AdminRegistrationBid;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.exception.ConflictException;
import ru.itmo.is.server.exception.UnauthorizedException;
import ru.itmo.is.server.web.JwtManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AuthService {
    private static final String PASSWORD_HASH_ALGORITHM = "SHA-384";

    @Inject
    private UserDao userDao;
    @Inject
    private ModelMapper mapper;
    @Inject
    private JwtManager jwtManager;

    @Transactional
    public Optional<JwtResponse> register(RegisterRequest req) {
        var user = mapper.map(req, User.class);
        if (userDao.isLoginBusy(user.getLogin()))
            throw new ConflictException("Login '" + user.getLogin() + "' is already in use");

        if (user.getRole() == Role.USER || userDao.getAdmins().isEmpty()) {
            userDao.save(user);
            return Optional.of(new JwtResponse(jwtManager.createToken(user)));
        }

        userDao.save(mapper.map(user, AdminRegistrationBid.class));
        return Optional.empty();
    }

    public JwtResponse login(LoginRequest req) {
        var userO = userDao.getUser(req.getLogin());
        if (userO.isEmpty()) throw new UnauthorizedException("Invalid login or password");
        if (!userO.get().getPassword().equals(hash384(req.getPassword())))
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

    private String hash384(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM);
            byte[] messageDigest = md.digest(source.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hash = new StringBuilder(no.toString(16));
            while (hash.length() < 32) {
                hash.insert(0, "0");
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
