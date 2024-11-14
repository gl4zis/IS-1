package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dao.LocationDao;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.mapper.LocationMapper;

import java.util.List;

@RequestScoped
public class LocationService {
    @Inject
    private LocationMapper mapper;
    @Inject
    private LocationDao locationDao;

    public List<LocationResponse> getAll() {
        return mapper.toDto(locationDao.getAll());
    }

    public LocationResponse get(int id) {
        var locationO = locationDao.getO(id);
        if (locationO.isEmpty()) throw new NotFoundException();
        return mapper.toDto(locationO.get());
    }

    @Transactional
    public void create(LocationRequest req) {
        locationDao.save(mapper.toEntity(req));
    }

    @Transactional
    public void delete(int id) {
        var locationO = locationDao.getO(id);
        if (locationO.isEmpty()) throw new NotFoundException();
        locationDao.delete(id);
    }

    @Transactional
    public void update(int id, LocationRequest req) {
        var locationO = locationDao.getO(id);
        if (locationO.isEmpty()) throw new NotFoundException();
        locationDao.update(mapper.toEntity(req, locationO.get()));
    }
}
