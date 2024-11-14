package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.CoordDao;
import ru.itmo.is.server.dao.LocationDao;
import ru.itmo.is.server.dao.PersonDao;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;

import java.util.List;

@RequestScoped
public class PersonService {
    @Inject
    private ModelMapper mapper;
    @Inject
    private PersonDao personDao;
    @Inject
    private LocationDao locationDao;
    @Inject
    private CoordDao coordDao;

    public List<PersonResponse> getAll() {
        return personDao.getAll().stream().map(person -> mapper.map(person, PersonResponse.class)).toList();
    }

    public PersonResponse get(int id) {
        var personO = personDao.getO(id);
        if (personO.isEmpty()) throw new NotFoundException();
        return mapper.map(personO.get(), PersonResponse.class);
    }

    @Transactional
    public void create(PersonRequest req) {
        personDao.save(toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var personO = personDao.getO(id);
        if (personO.isEmpty()) throw new NotFoundException();
        personDao.delete(id);
    }

    @Transactional
    public void update(int id, PersonRequest req) {
        var personO = personDao.getO(id);
        if (personO.isEmpty()) throw new NotFoundException();

        var person = toEntity(req);
        person.setId(id);
        person.setCreatedBy(personO.get().getCreatedBy());
        person.setCreatedAt(personO.get().getCreatedAt());
        personDao.update(person);
    }

    public double getAllHeightSum() {
        double sum = 0;
        var people = personDao.getAll();
        for (Person p : people) {
            sum += p.getHeight();
        }
        return sum;
    }

    public PersonResponse getPersonWithMaxCoords() {
        var people = personDao.getAll();
        if (people.isEmpty()) throw new NotFoundException("Zero people saved");
        var maxPersonIdx = 0;
        var maxCoord = people.get(maxPersonIdx).getCoordinates();
        for (Person p : people) {
            if (p.getCoordinates().compareTo(maxCoord) > 0) {
                maxCoord = p.getCoordinates();
            }
        }
        return mapper.map(people.get(maxPersonIdx), PersonResponse.class);
    }

    public long countPeopleByWeight(long weight) {
        return personDao.countPeopleByWeight(weight);
    }

    public long countPeopleByEyeColor(Color eyeColor) {
        return personDao.countPeopleByEyeColor(eyeColor);
    }

    public float getPeopleProportionByEyeColor(Color eyeColor) {
        return (float) personDao.countPeopleByEyeColor(eyeColor) / personDao.countAllPeople();
    }
    
    private Person toEntity(PersonRequest req) {
        var person = mapper.map(req, Person.class);

        if (req.getCoord() != null) {
            var coord = mapper.map(req.getCoord(), Coordinates.class);
            coordDao.save(coord);
            person.setCoordinates(coord);
        } else if (req.getCoordId() != null) {
            var coordO = coordDao.getO(req.getCoordId());
            if (coordO.isEmpty()) throw new BadRequestException("Coordinates with such id not exists");
            person.setCoordinates(coordO.get());
        } else throw new BadRequestException("Invalid PersonRequest");

        if (req.getLocationId() != null) {
            var locationO = locationDao.getO(req.getLocationId());
            if (locationO.isEmpty()) throw new BadRequestException("Location with such id not exists");
            person.setLocation(locationO.get());
        } else if (req.getLocation() != null) {
            var location = mapper.map(req.getLocation(), Location.class);
            locationDao.save(location);
            person.setLocation(location);
        }
        return person;
    }
}
