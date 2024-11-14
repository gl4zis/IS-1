package ru.itmo.is.server.mapper;

import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.itmo.is.server.dto.request.PersonRequest;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Person;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class PersonMapper extends EntityMapper<Person, PersonRequest, PersonResponse> {
    @Inject
    protected CoordMapper coordMapper;
    @Inject
    protected LocationMapper locationMapper;

    @Override
    @Mappings({
            @Mapping(target = "accessible", expression = "java(activeUser.hasAccess(person))"),
            @Mapping(target = "coordinates", expression = "java(coordMapper.toDto(person.getCoordinates()))"),
            @Mapping(target = "location", expression = "java(locationMapper.toDto(person.getLocation()))")
    })
    public abstract PersonResponse toDto(Person person);

    @Override
    @Mappings({
            @Mapping(target = "coordinates", expression = "java(coordMapper.toEntity(person.getCoordinates()))"),
            @Mapping(target = "location", expression = "java(locationMapper.toEntity(person.getLocation()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedBy", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    public abstract Person toEntity(PersonRequest person);
}
