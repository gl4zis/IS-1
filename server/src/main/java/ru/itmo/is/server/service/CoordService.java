package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.mapper.CoordMapper;

import java.util.List;

@ApplicationScoped
public class CoordService {
    @PersistenceContext
    protected EntityManager em;
    @Inject
    protected CoordMapper mapper;

    public List<CoordResponse> getAll() {
        return mapper.toDto(em.createNamedQuery("Coordinates.findAll", Coordinates.class).getResultList());
    }

    public CoordResponse get(int id) {
        var entity = em.find(Coordinates.class, id);
        if (entity == null) throw new NotFoundException();
        return mapper.toDto(entity);
    }

    @Transactional
    public void create(CoordRequest req) {
        em.persist(mapper.toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var entity = em.find(Coordinates.class, id);
        if (entity == null) throw new NotFoundException();
        em.remove(entity);
    }

    @Transactional
    public void update(int id, CoordRequest req) {
        var entity = em.find(Coordinates.class, id);
        if (entity == null) throw new NotFoundException();
        em.merge(mapper.toEntity(req, entity));
    }
}
