package ru.itmo.is.server.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.request.LocationRequest;

@Getter
@Setter
public class LocationResponse extends LocationRequest {
    private Integer id;
    private boolean accessible;
}
