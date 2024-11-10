package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is.server.entity.security.Role;

@Data
public class RegisterRequest {
    @NotEmpty
    private String credentials;
    @NotNull
    private Role role;
}
