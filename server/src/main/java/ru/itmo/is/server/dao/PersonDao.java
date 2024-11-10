package ru.itmo.is.server.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.itmo.is.server.entity.Person;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonDao {
    @PersistenceContext
    private EntityManager em;

    public List<Person> getAll() {
        return em.createQuery("FROM Person", Person.class)
                .getResultList();
    }

    public Optional<Person> getO(int id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    public void save(Person person) {
        em.persist(person);
    }

    public void delete(int id) {
        var person = em.find(Person.class, id);
        if (person != null) em.remove(person);
    }

    public void update(Person person) {
        em.merge(person);
    }
}
