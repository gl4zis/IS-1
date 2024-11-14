package ru.itmo.is.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public abstract class LocationDto {
    @NotNull
    private Long x;
    @NotNull
    private Double y;
    @NotNull
    @Length(max = 312)
    private String name;
}
