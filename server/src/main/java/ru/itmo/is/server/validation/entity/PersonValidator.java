package ru.itmo.is.server.validation.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ValidationException;
import ru.itmo.is.server.entity.Person;

@ApplicationScoped
public class PersonValidator extends EntityValidator<Person> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(Person person) {
        super.validate(person);
        if (!isNameUnique(person.getName())) {
            throw new ValidationException("Person with name " + person.getName() + " already exists");
        }
        if (!isPassportUnique(person.getPassportId())) {
            throw new ValidationException("Person with passport " + person.getPassportId() + " already exists");
        }
    }

    public boolean isNameUnique(String name) {
        return em.createNamedQuery("Person.isNameUnique", boolean.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public boolean isPassportUnique(String passportId) {
        return em.createNamedQuery("Person.isPassportUnique", boolean.class)
                .setParameter("passportId", passportId)
                .getSingleResult();
    }
}
