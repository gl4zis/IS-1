package ru.itmo.is.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Person;

@Mapper(
        componentModel = MappingConstants.ComponentModel.JAKARTA_CDI,
        uses = { CoordMapper.class, LocationMapper.class }
)
public abstract class PersonMapper extends EntityMapper<Person, PersonRequest, PersonResponse> {
}
