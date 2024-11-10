package ru.itmo.is.server.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.itmo.is.server.entity.security.AdminRegistrationBid;
import ru.itmo.is.server.entity.security.User;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDao {
    @PersistenceContext
    private EntityManager em;

    public Optional<User> getUser(String login) {
        return Optional.ofNullable(em.find(User.class, login));
    }

    public void saveUser(User user) {
        em.persist(user);
    }

    public void saveAdminBid(AdminRegistrationBid adminBid) {
        em.persist(adminBid);
    }

    public boolean isLoginBusy(String login) {
        var user = em.find(User.class, login);
        if (user != null) return true;
        var bid = em.find(AdminRegistrationBid.class, login);
        return bid != null;
    }

    public List<User> getAdmins() {
        return em.createQuery("FROM User u WHERE u.role = 'ADMIN'", User.class)
                .getResultList();
    }
}
