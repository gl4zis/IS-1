package ru.itmo.is.server.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.PersonDto;
import ru.itmo.is.server.validation.ValidPerson;

@Getter
@Setter
@ValidPerson
public class PersonRequest extends PersonDto {
    private Integer coordId;
    @Valid
    private CoordRequest coordinates;
    private Integer locationId;
    @Valid
    private LocationRequest location;
    private boolean adminAccess;
}
