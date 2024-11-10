package ru.itmo.is.server.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.itmo.is.server.entity.Coordinates;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CoordDao {
    @PersistenceContext
    private EntityManager em;

    public List<Coordinates> getAll() {
        return em.createQuery("FROM Coordinates", Coordinates.class)
                .getResultList();
    }

    public Optional<Coordinates> getO(int id) {
        return Optional.ofNullable(em.find(Coordinates.class, id));
    }

    public void save(Coordinates coordinates) {
        em.persist(coordinates);
    }

    public void delete(int id) {
        var coord = em.find(Coordinates.class, id);
        if (coord != null) em.remove(coord);
    }

    public void update(Coordinates coordinates) {
        em.merge(coordinates);
    }
}
