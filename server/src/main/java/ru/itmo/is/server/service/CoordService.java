package ru.itmo.is.server.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.itmo.is.server.dao.CoordDao;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.mapper.CoordMapper;

import java.util.List;

@RequestScoped
public class CoordService {
    @Inject
    private CoordMapper mapper;
    @Inject
    private CoordDao coordDao;

    public List<CoordResponse> getAll() {
        return mapper.toDto(coordDao.getAll());
    }

    public CoordResponse get(int id) {
        var coordO = coordDao.getO(id);
        if (coordO.isEmpty()) throw new NotFoundException();
        return mapper.toDto(coordO.get());
    }

    @Transactional
    public void create(CoordRequest req) {
        coordDao.save(mapper.toEntity(req));
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
        coordDao.update(mapper.toEntity(req, coordO.get()));
    }
}
