package ru.itmo.is.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.entity.Location;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class LocationMapper extends EntityMapper<Location, LocationRequest, LocationResponse> {
}
