package ru.itmo.is.server.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.LocationDto;

@Getter
@Setter
public class LocationRequest extends LocationDto {
    private boolean adminAccess;
}
