package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.LocationDao;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.entity.Location;

import java.util.List;

@RequestScoped
public class LocationService {
    @Inject
    private ModelMapper mapper;
    @Inject
    private LocationDao locationDao;

    public List<LocationResponse> getAll() {
        return locationDao.getAll().stream().map(coord -> mapper.map(coord, LocationResponse.class)).toList();
    }

    public LocationResponse get(int id) {
        var location = locationDao.getO(id);
        if (location.isEmpty()) throw new NotFoundException();
        return mapper.map(location, LocationResponse.class);
    }

    @Transactional
    public void create(LocationRequest req) {
        locationDao.save(mapper.map(req, Location.class));
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
        var location = locationO.get();
        location.setName(req.getName());
        location.setX(req.getX());
        location.setY(req.getY());
        locationDao.update(location);
    }
}
