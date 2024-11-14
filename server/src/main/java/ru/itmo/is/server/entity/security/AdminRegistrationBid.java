package ru.itmo.is.server.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_bid")
@Getter
@Setter
@NamedQuery(name = "AdminRegistrationBid.findAllLogins", query = "SELECT b.login FROM AdminRegistrationBid b")
public class AdminRegistrationBid {
    @Id
    private String login;

    @NotNull
    private String password;
}
