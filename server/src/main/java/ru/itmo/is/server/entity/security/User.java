package ru.itmo.is.server.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "usr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    private String login;

    @NotNull
    @EqualsAndHashCode.Exclude
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
