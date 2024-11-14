package ru.itmo.is.server.web;

import jakarta.enterprise.context.RequestScoped;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;
import ru.itmo.is.server.entity.util.AbstractEntity;

import java.util.function.Supplier;

@RequestScoped
public class ActiveUserHolder implements Supplier<User> {
    private User user;

    public boolean isAdmin() {
        return user.getRole() == Role.ADMIN;
    }

    @Override
    public User get() {
        return user;
    }

    public void set(User user) {
        this.user = user;
    }

    public boolean hasAccess(AbstractEntity entity) {
        return entity.getCreatedBy().equals(user) || (isAdmin() && entity.isAdminAccess());
    }
}
