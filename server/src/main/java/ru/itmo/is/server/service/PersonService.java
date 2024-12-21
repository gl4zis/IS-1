package ru.itmo.is.server.service;

import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;

@ApplicationScoped
public class PersonService extends BaseEntityService<Person, PersonRequest, PersonResponse> {
    public PersonService() {
        super(Person.class);
    }

    @Override
    public void validate(Person person) {
        if (!isNameUnique(person.getName())) {
            throw new BadRequestException("Person with name " + person.getName() + " already exists");
        }
        if (!isPassportUnique(person.getPassportId())) {
            throw new BadRequestException("Person with passport " + person.getPassportId() + " already exists");
        }
        em.persist(person);
    }

    public double getAllHeightSum() {
        return em.createNamedQuery("Person.getHeightSum", Double.class).getSingleResult();
    }

    public PersonResponse getPersonWithMaxCoords() {
        return mapper.toDto(em.createNamedQuery("Person.findMaxByCoord", Person.class)
                .setMaxResults(1)
                .getSingleResult());
    }

    public long countPeopleByWeight(long weight) {
        return em.createNamedQuery("Person.countByWeight", Long.class)
                .setParameter("weight", weight).getSingleResult();
    }

    public long countPeopleByEyeColor(Color eyeColor) {
        return em.createNamedQuery("Person.countByEyeColor", Long.class)
                .setParameter("eyeColor", eyeColor).getSingleResult();
    }

    public float getPeopleProportionByEyeColor(Color eyeColor) {
        var countByColor = em.createNamedQuery("Person.countByEyeColor", Long.class)
                .setParameter("eyeColor", eyeColor).getSingleResult().floatValue();
        var count = em.createNamedQuery("Person.count", Long.class).getSingleResult();
        return 100 * countByColor / count;
    }

    @Override
    protected Person toEntity(PersonRequest req, @Nullable Person origin) {
        var person = origin == null ? mapper.toEntity(req) : mapper.toEntity(req, origin);

        if (person.getCoordinates() != null) {
            em.persist(person.getCoordinates());
        } else if (req.getCoordId() != null) {
            var coord = em.find(Coordinates.class, req.getCoordId());
            if (coord == null) throw new NotFoundException("Coordinates with such id not exists");
            person.setCoordinates(coord);
        } else throw new BadRequestException("Invalid PersonRequest");

        if (person.getLocation() != null) {
            em.persist(person.getLocation());
        } else if (req.getLocationId() != null) {
            var location = em.find(Location.class, req.getLocationId());
            if (location == null) throw new NotFoundException("Location with such id not exists");
            person.setLocation(location);
        }

        return person;
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
