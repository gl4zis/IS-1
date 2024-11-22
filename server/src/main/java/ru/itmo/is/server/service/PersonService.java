package ru.itmo.is.server.service;

import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.request.RelinkRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.ws.SubscriptionType;
import ru.itmo.is.server.ws.WsSubscription;

@ApplicationScoped
public class PersonService extends BaseEntityService<Person, PersonRequest, PersonResponse> {
    public PersonService() {
        super(Person.class);
    }

    @Override
    @Transactional
    public void create(PersonRequest req) {
        em.persist(toEntity(req, null));
        WsSubscription.onUpdate(SubscriptionType.PERSON, getAll());
    }

    @Override
    @Transactional
    public void update(int id, PersonRequest req) {
        em.merge(toEntity(req, find(id)));
        WsSubscription.onUpdate(SubscriptionType.PERSON, getAll());
    }

    @Override
    @Transactional
    public void delete(int id) {
        em.remove(find(id));
        WsSubscription.onUpdate(SubscriptionType.PERSON, getAll());
    }

    public double getAllHeightSum() {
        return findAll().stream().mapToDouble(Person::getHeight).sum();
    }

    public PersonResponse getPersonWithMaxCoords() {
        var people = findAll();
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

    @Transactional
    public void relink(int personId, RelinkRequest req) {
        var p = find(personId);
        var c = em.find(Coordinates.class, req.getCoordId());
        if (c == null) throw new NotFoundException("Coordinates not found");
        p.setCoordinates(c);
        p.setLocation(null);
        if (req.getLocationId() != null) {
            var l = em.find(Location.class, req.getLocationId());
            if (l == null) throw new NotFoundException("Location not found");
            p.setLocation(l);
        }
        em.merge(p);
    }
    
    private Person toEntity(PersonRequest req, @Nullable Person origin) {
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
}
