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
        personDao.save(mapPerson(req));
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

        var person = mapPerson(req);
        person.setId(id);
        person.setCreatedBy(personO.get().getCreatedBy());
        person.setCreatedAt(personO.get().getCreatedAt());
        personDao.update(person);
    }
    
    private Person mapPerson(PersonRequest req) {
        var person = mapper.map(req, Person.class);

        var coordO = coordDao.getO(req.getCoordId());
        if (coordO.isEmpty()) throw new BadRequestException("Coordinates with such id not exists");
        person.setCoordinates(coordO.get());

        if (req.getLocationId() != null) {
            var locationO = locationDao.getO(req.getLocationId());
            if (locationO.isEmpty()) throw new BadRequestException("Location with such id not exists");
            person.setLocation(locationO.get());
        }
        return person;
    }
}
