package ru.itmo.is.server.dto.response;

import jakarta.validation.constraints.NotNull;

public record JwtResponse(@NotNull String token) { }
