package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.mapper.PersonMapper;

import java.util.List;

@ApplicationScoped
public class PersonService {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private PersonMapper mapper;

    public List<PersonResponse> getAll() {
        return mapper.toDto(getAllEntities());
    }

    public PersonResponse get(int id) {
        var person = em.find(Person.class, id);
        if (person == null) throw new NotFoundException();
        return mapper.toDto(person);
    }

    @Transactional
    public void create(PersonRequest req) {
        em.persist(toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var person = em.find(Person.class, id);
        if (person == null) throw new NotFoundException();
        em.remove(person);
    }

    @Transactional
    public void update(int id, PersonRequest req) {
        var person = em.find(Person.class, id);
        if (person == null) throw new NotFoundException();
        em.merge(mapper.toEntity(req, person));
    }

    public double getAllHeightSum() {
        double sum = 0;
        var people = getAllEntities();
        for (Person p : people) {
            sum += p.getHeight();
        }
        return sum;
    }

    public PersonResponse getPersonWithMaxCoords() {
        var people = getAllEntities();
        if (people.isEmpty()) throw new NotFoundException("Zero people saved");
        var maxPersonIdx = 0;
        var maxCoord = people.get(maxPersonIdx).getCoordinates();
        for (Person p : people) {
            if (p.getCoordinates().compareTo(maxCoord) > 0) {
                maxCoord = p.getCoordinates();
            }
        }
        return mapper.toDto(people.get(maxPersonIdx));
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
        return countByColor / count;
    }
    
    private Person toEntity(PersonRequest req) {
        var person = mapper.toEntity(req);

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

    private List<Person> getAllEntities() {
        return em.createNamedQuery("Person.findAll", Person.class).getResultList();
    }
}
