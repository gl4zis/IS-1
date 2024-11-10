package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.Role;

@Getter
@Setter
public class RegisterRequest extends LoginRequest {
    @NotNull
    private Role role;
}
