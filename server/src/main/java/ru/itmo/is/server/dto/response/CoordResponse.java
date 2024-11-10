package ru.itmo.is.server.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.dto.request.CoordRequest;

@Getter
@Setter
public class CoordResponse extends CoordRequest {
    private Integer id;
}
