package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import ru.itmo.is.server.dao.CoordDao;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.entity.Coordinates;

import java.util.List;

@RequestScoped
public class CoordService {
    @Inject
    private ModelMapper mapper;
    @Inject
    private CoordDao coordDao;

    public List<CoordResponse> getAll() {
        return coordDao.getAll().stream().map(coord -> mapper.map(coord, CoordResponse.class)).toList();
    }

    public CoordResponse get(int id) {
        var coordO = coordDao.getO(id);
        if (coordO.isEmpty()) throw new NotFoundException();
        return mapper.map(coordO.get(), CoordResponse.class);
    }

    @Transactional
    public void create(CoordRequest req) {
        coordDao.save(mapper.map(req, Coordinates.class));
    }

    @Transactional
    public void delete(int id) {
        var coordO = coordDao.getO(id);
        if (coordO.isEmpty()) throw new NotFoundException();
        coordDao.delete(id);
    }

    @Transactional
    public void update(int id, CoordRequest req) {
        var coordO = coordDao.getO(id);
        if (coordO.isEmpty()) throw new NotFoundException();
        var coord = coordO.get();
        coord.setX(req.getX());
        coord.setY(req.getY());
        coordDao.update(coord);
    }
}
