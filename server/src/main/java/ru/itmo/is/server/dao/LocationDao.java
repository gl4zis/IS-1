package ru.itmo.is.server.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.itmo.is.server.entity.Location;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LocationDao {
    @PersistenceContext
    private EntityManager em;

    public List<Location> getAll() {
        return em.createQuery("FROM Location", Location.class)
                .getResultList();
    }

    public Optional<Location> getO(int id) {
        return Optional.ofNullable(em.find(Location.class, id));
    }

    public void save(Location location) {
        em.persist(location);
    }

    public void delete(int id) {
        var location = em.find(Location.class, id);
        if (location != null) em.remove(location);
    }

    public void update(Location location) {
        em.merge(location);
    }
}
