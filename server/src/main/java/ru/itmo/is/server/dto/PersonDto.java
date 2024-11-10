package ru.itmo.is.server.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.server.entity.Color;
import ru.itmo.is.server.entity.Country;

@Getter
@Setter
public class PersonDto {
    @NotBlank
    private String name;
    @NotNull
    private Color eyeColor;
    @NotNull
    private Color hairColor;
    @Positive
    private float height;
    @NotNull
    @Min(1)
    private Long weight;
    @NotNull
    @Length(min = 4)
    private String passportId;
    private Country nationality;
}
