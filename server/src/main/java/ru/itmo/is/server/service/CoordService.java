package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.mapper.CoordMapper;

@ApplicationScoped
public class CoordService extends BaseEntityService<Coordinates, CoordRequest, CoordResponse, CoordMapper> {
    public CoordService() {
        super(Coordinates.class);
    }
}
