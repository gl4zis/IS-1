package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.mapper.LocationMapper;

@ApplicationScoped
public class LocationService extends BaseEntityService<Location, LocationRequest, LocationResponse, LocationMapper> {
    public LocationService() {
        super(Location.class);
    }
}
