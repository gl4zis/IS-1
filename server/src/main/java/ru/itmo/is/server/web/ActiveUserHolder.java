package ru.itmo.is.server.web;

import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import ru.itmo.is.server.entity.security.Role;
import ru.itmo.is.server.entity.security.User;

@RequestScoped
@Getter
public class ActiveUserHolder {
    private String login;
    private Role role;

    public void setUser(User user) {
        this.login = user.getLogin();
        this.role = user.getRole();
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
