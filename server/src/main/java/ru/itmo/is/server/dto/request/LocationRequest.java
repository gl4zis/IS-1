package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LocationRequest {
    @NotNull
    private Long x;
    @NotNull
    private Double y;
    @NotNull
    @Length(max = 312)
    private String name;
    private boolean adminAccess;
}
