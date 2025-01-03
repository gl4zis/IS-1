package ru.itmo.is.server.mapper;

import jakarta.inject.Inject;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.itmo.is.server.entity.util.AbstractEntity;
import ru.itmo.is.server.web.ActiveUserHolder;

import java.util.Collection;
import java.util.List;

public abstract class EntityMapper<E extends AbstractEntity, REQ, RES> {
    @Inject
    protected ActiveUserHolder activeUser;

    @Mapping(target = "accessible", expression = "java(activeUser.hasAccess(entity))")
    public abstract RES toDto(E entity);

    public abstract List<RES> toDto(Collection<E> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdAt", ignore = true)
    })
    public abstract E toEntity(REQ req);

    public E toEntity(REQ req, E origin) {
        E entity = toEntity(req);
        entity.setId(origin.getId());
        entity.setCreatedAt(origin.getCreatedAt());
        entity.setCreatedBy(origin.getCreatedBy());
        entity.setAdminAccess(origin.isAdminAccess());
        return entity;
    }
}
