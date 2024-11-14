package ru.itmo.is.server.entity.util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.ws.rs.ForbiddenException;
import ru.itmo.is.server.service.AuthService;
import ru.itmo.is.server.web.ActiveUserHolder;

import java.time.LocalDateTime;

@RequestScoped
public class AbstractEntityListener {
    @Inject
    private ActiveUserHolder userHolder;


    @PrePersist
    public void prePersist(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            entity.setCreatedBy(userHolder.get());
            entity.setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            validateAccess(entity);
            entity.setUpdatedBy(userHolder.get());
            entity.setUpdatedAt(LocalDateTime.now());
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
