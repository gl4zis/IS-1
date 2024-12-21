package ru.itmo.is.server.service;

import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;
import ru.itmo.is.server.dto.response.SelectResponse;
import ru.itmo.is.server.entity.util.AbstractEntity;
import ru.itmo.is.server.mapper.EntityMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseEntityService<E extends AbstractEntity, REQ, RES> {
    @PersistenceContext
    protected EntityManager em;
    @Inject
    protected EntityMapper<E, REQ, RES> mapper;
    private final Class<E> eClass;

    @Transactional
    public void create(REQ req) {
        var e = toEntity(req, null);
        validate(e);
        em.persist(e);
    }

    @Transactional
    public void delete(int id) {
        var entity = find(id);
        em.remove(entity);
    }

    @Transactional
    public void update(int id, REQ req) {
        var e = toEntity(req, find(id));
        validate(e);
        em.merge(e);
    }

    public List<RES> getFiltered(FilteredRequest req) {
        return mapper.toDto(em.createQuery(req.toJPQL(), eClass).getResultList());
    }

    public int countFiltered(Map<String, String> filters) {
        StringBuilder builder = new StringBuilder().append("SELECT COUNT(*) FROM ").append(eClass.getSimpleName());
        FilteredRequest.filtersToJPQL(filters, builder);
        return em.createQuery(builder.toString(), Long.class).getSingleResult().intValue();
    }

    public List<SelectResponse> getForSelect() {
        return em.createQuery("SELECT new ru.itmo.is.server.dto.response.SelectResponse(e.id, e.name)" +
                        " FROM " + eClass.getSimpleName() + " e ORDER BY e.name", SelectResponse.class)
                .getResultList();
    }

    protected E find(int id) {
        var e = em.find(eClass, id);
        if (e == null) throw new NotFoundException();
        return e;
    }

    public void validate(E entity) {
        return;
    }

    protected E toEntity(REQ req, @Nullable E origin) {
        return origin == null ? mapper.toEntity(req) : mapper.toEntity(req, origin);
    }
}
