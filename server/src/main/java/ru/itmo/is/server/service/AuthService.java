package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.UserDao;
import ru.itmo.is.server.dto.request.LoginRequest;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.dto.response.JwtResponse;
import ru.itmo.is.server.entity.security.AdminRegistrationBid;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.exception.AuthorizationException;
import ru.itmo.is.server.exception.InvalidRequestException;
import ru.itmo.is.server.exception.LoginIsBusyException;
import ru.itmo.is.server.exception.SignInException;
import ru.itmo.is.server.web.JwtManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserDao userDao;
    @Inject
    private ModelMapper mapper;
    @Inject
    private JwtManager jwtManager;

    @Transactional
    public Optional<JwtResponse> register(RegisterRequest req) {
        var user = mapUser(req);
        if (userDao.isLoginBusy(user.getLogin())) throw new LoginIsBusyException(user.getLogin());

        if (user.getRole() == Role.USER || userDao.getAdmins().isEmpty()) {
            userDao.saveUser(user);
            return Optional.of(new JwtResponse(jwtManager.createToken(user)));
        }

        userDao.saveAdminBid(mapper.map(user, AdminRegistrationBid.class));
        return Optional.empty();
    }

    public JwtResponse login(LoginRequest req) {
        var credentials = mapCredentials(req.getCredentials());
        var userO = userDao.getUser(credentials.getLogin());
        if (userO.isEmpty()) throw new SignInException();
        if (!userO.get().getPassword().equals(credentials.getPassword())) throw new SignInException();

        return new JwtResponse(jwtManager.createToken(userO.get()));
    }

    private String hash384(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
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

    private User mapUser(RegisterRequest req) {
        User user = mapCredentials(req.getCredentials());
        user.setRole(req.getRole());
        return user;
    }

    private User mapCredentials(String credentials) {
        try {
            var decoded = new String(Base64.getDecoder().decode(credentials));
            var login = decoded.split(":")[0];
            var password = decoded.split(":")[1];
            return new User(login, hash384(password), null);
        } catch (Exception e) {
            throw new InvalidRequestException();
        }
    }

    public User getUser(String jwt) {
        var login = jwtManager.getLogin(jwt);
        var role = jwtManager.getRole(jwt);

        var user = userDao.getUser(login);
        if (user.isEmpty()) throw new AuthorizationException();
        if (!user.get().getRole().equals(role)) throw new AuthorizationException();
        return user.get();
    }
}
