package ru.itmo.is.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.entity.Coordinates;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class CoordMapper extends EntityMapper<Coordinates, CoordRequest, CoordResponse> {
}
