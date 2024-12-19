package ru.itmo.is.server.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;
import ru.itmo.is.server.entity.util.AbstractEntity;
import ru.itmo.is.server.mapper.EntityMapper;
import ru.itmo.is.server.web.ActiveUserHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseEntityService<E extends AbstractEntity, REQ, RES> {
    @PersistenceContext
    protected EntityManager em;
    @Inject
    protected EntityMapper<E, REQ, RES> mapper;
    @Inject
    protected ActiveUserHolder userHolder;
    private final Class<E> eClass;

    public List<RES> getAll() {
        return mapper.toDto(em.createNamedQuery(eClass.getSimpleName() + ".findAll", eClass).getResultList());
    }

    public RES get(int id) {
        return mapper.toDto(find(id));
    }

    @Transactional
    public void create(REQ req) {
        em.persist(mapper.toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var entity = find(id);
        entity.setRemovedAt(LocalDateTime.now());
        entity.setRemovedBy(userHolder.get());
        em.merge((AbstractEntity) entity);
        em.remove(entity);
    }

    @Transactional
    public void update(int id, REQ req) {
        em.merge(mapper.toEntity(req, find(id)));
    }

    public List<RES> getFiltered(FilteredRequest req) {
        return mapper.toDto(em.createQuery(req.toJPQL(), eClass).getResultList());
    }

    public int countFiltered(Map<String, String> filters) {
        StringBuilder builder = new StringBuilder().append("SELECT COUNT(*) FROM ").append(eClass.getSimpleName());
        FilteredRequest.filtersToJPQL(filters, builder);
        return em.createQuery(builder.toString(), Long.class).getSingleResult().intValue();
    }

    protected E find(int id) {
        var e = em.find(eClass, id);
        if (e == null) throw new NotFoundException();
        return e;
    }

    protected List<E> findAll() {
        return em.createNamedQuery(eClass.getSimpleName() + ".findAll", eClass).getResultList();
    }
}
