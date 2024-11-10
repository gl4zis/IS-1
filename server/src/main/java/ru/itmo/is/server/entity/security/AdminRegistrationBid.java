package ru.itmo.is.server.entity.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_bid")
@Getter
@Setter
public class AdminRegistrationBid {
    @Id
    private String login;

    @NotNull
    private String password;
}
