package ru.itmo.is.server.dto.response;

import lombok.*;
import ru.itmo.is.server.dto.CoordDto;

@Getter
@Setter
public class CoordResponse extends CoordDto {
    private Integer id;
    private boolean accessible;
    private boolean adminAccess;
}
