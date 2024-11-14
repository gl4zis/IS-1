package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.mapper.LocationMapper;

import java.util.List;

@ApplicationScoped
public class LocationService {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private LocationMapper mapper;

    public List<LocationResponse> getAll() {
        return mapper.toDto(em.createNamedQuery("Location.findAll", Location.class).getResultList());
    }

    public LocationResponse get(int id) {
        var location = em.find(Location.class, id);
        if (location == null) throw new NotFoundException();
        return mapper.toDto(location);
    }

    @Transactional
    public void create(LocationRequest req) {
        em.persist(mapper.toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var location = em.find(Location.class, id);
        if (location == null) throw new NotFoundException();
        em.remove(location);
    }

    @Transactional
    public void update(int id, LocationRequest req) {
        var location = em.find(Location.class, id);
        if (location == null) throw new NotFoundException();
        em.merge(mapper.toEntity(req, location));
    }
}
