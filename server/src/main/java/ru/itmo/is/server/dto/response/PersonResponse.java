package ru.itmo.is.server.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.PersonDto;

@Getter
@Setter
public class PersonResponse extends PersonDto {
    private Integer id;
    private CoordResponse coordinates;
    private LocationResponse location;
}
