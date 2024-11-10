package ru.itmo.is.server.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
