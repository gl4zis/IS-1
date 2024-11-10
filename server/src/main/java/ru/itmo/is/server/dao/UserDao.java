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

    public Optional<AdminRegistrationBid> getAdminBid(String login) {
        return Optional.ofNullable(em.find(AdminRegistrationBid.class, login));
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

    public List<AdminRegistrationBid> getAdminBids() {
        return em.createQuery("FROM AdminRegistrationBid", AdminRegistrationBid.class)
                .getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }

    public void save(AdminRegistrationBid adminBid) {
        em.persist(adminBid);
    }

    public void deleteBid(String login) {
        em.createQuery("DELETE FROM AdminRegistrationBid bid WHERE bid.login = :login")
                .setParameter("login", login)
                .executeUpdate();
    }
}
