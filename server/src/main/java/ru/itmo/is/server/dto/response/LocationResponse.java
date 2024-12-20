package ru.itmo.is.server.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.LocationDto;

@Getter
@Setter
public class LocationResponse extends LocationDto {
    private Integer id;
    private boolean accessible;
    private boolean adminAccess;
}
