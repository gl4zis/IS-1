package ru.itmo.is.server.validation.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ValidationException;
import ru.itmo.is.server.entity.Location;

@ApplicationScoped
public class LocationValidator extends EntityValidator<Location> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(Location location) {
        super.validate(location);
        if (!isNameUnique(location.getName())) {
            throw new ValidationException("Location with name " + location.getName() + " already exists");
        }
    }

    public boolean isNameUnique(String name) {
        return em.createNamedQuery("Location.isNameUnique", boolean.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
