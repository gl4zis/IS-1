package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.UserDao;
import ru.itmo.is.server.dto.request.RegisterRequest;
import ru.itmo.is.server.entity.security.AdminRegistrationBid;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.exception.InvalidRequestException;
import ru.itmo.is.server.exception.LoginIsBusyException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserDao userDao;
    @Inject
    private ModelMapper mapper;

    @Transactional
    public Response.Status register(RegisterRequest req) {
        var user = mapUser(req);
        if (userDao.isLoginBusy(user.getLogin())) throw new LoginIsBusyException(user.getLogin());

        if (user.getRole() == Role.USER || userDao.getAdmins().isEmpty()) {
            userDao.saveUser(user);
            return Response.Status.CREATED;
        }

        userDao.saveAdminBid(mapper.map(user, AdminRegistrationBid.class));
        return Response.Status.ACCEPTED;
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
        User user = getCredentials(req.getCredentials());
        user.setRole(req.getRole());
        return user;
    }

    private User getCredentials(String credentials) {
        try {
            var decoded = new String(Base64.getDecoder().decode(credentials));
            var login = decoded.split(":")[0];
            var password = decoded.split(":")[1];
            return new User(login, hash384(password), null);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidRequestException();
        }
    }
}
