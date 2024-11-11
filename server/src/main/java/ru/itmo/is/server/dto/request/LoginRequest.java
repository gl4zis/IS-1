package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    /**
     * Format: Base64.encode(user:password)
     */
    @NotBlank
    private String credentials;
}
