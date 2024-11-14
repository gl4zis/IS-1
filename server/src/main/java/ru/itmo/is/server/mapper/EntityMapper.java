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
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedBy", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    public abstract E toEntity(REQ req);

    public E toEntity(REQ req, E edit) {
        E entity = toEntity(req);
        entity.setId(edit.getId());
        entity.setCreatedAt(edit.getCreatedAt());
        entity.setCreatedBy(edit.getCreatedBy());
        entity.setAdminAccess(edit.isAdminAccess());
        return entity;
    };
}
