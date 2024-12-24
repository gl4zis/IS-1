package ru.itmo.is.server.entity.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.ForbiddenException;
import ru.itmo.is.server.web.ActiveUserHolder;

import java.time.LocalDateTime;

@ApplicationScoped
public class EntityListener {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private ActiveUserHolder userHolder;

    @PrePersist
    public void prePersist(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            if (userHolder.get() != null) entity.setCreatedBy(userHolder.get());
            entity.setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            validateAccess(entity);
            em.persist(new EntityHistory(entity, userHolder.get()));
        }
    }

    @PreRemove
    public void preRemove(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            validateAccess(entity);
        }
    }

    public void validateAccess(AbstractEntity entity) {
        if (!userHolder.hasAccess(entity)) throw new ForbiddenException("Permission denied");
    }
}
