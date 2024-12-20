package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelinkRequest {
    @NotNull
    private Integer personId;
    @NotNull
    private Integer coordId;
    private Integer locationId;
}
