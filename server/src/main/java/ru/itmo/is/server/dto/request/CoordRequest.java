package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordRequest {
    private long x;

    @NotNull
    @Min(-725)
    private Double y;
}
