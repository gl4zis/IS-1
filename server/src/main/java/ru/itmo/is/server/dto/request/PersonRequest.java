package ru.itmo.is.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.PersonDto;

@Getter
@Setter
public class PersonRequest extends PersonDto {
    @NotNull
    private Integer coordId;
    private Integer locationId;
    private boolean adminAccess;
}
