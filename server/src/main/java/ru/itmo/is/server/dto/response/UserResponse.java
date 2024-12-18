package ru.itmo.is.server.dto.response;

import jakarta.validation.constraints.NotNull;
import ru.itmo.is.server.entity.security.Role;

public record UserResponse(@NotNull String token, @NotNull Role role) { }
