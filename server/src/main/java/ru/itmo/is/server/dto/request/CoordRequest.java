package ru.itmo.is.server.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.CoordDto;

@Getter
@Setter
public class CoordRequest extends CoordDto {
    private boolean adminAccess;
}
