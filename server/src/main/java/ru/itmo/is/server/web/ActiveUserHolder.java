package ru.itmo.is.server.web;

import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;

@RequestScoped
@Getter
@Setter
public class ActiveUserHolder {
    private User user;

    public boolean isAdmin() {
        return user.getRole() == Role.ADMIN;
    }
}
