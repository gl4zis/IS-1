package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.dto.response.SelectResponse;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.utils.StringUtils;

import java.util.List;

@ApplicationScoped
public class CoordService extends BaseEntityService<Coordinates, CoordRequest, CoordResponse> {
     public CoordService() {
        super(Coordinates.class);
    }

    @Override
    @Transactional
    public void delete(int id) {
        var linkedPeopleIds = getLinkedPeople(id).stream().map(SelectResponse::getId).toList();
        if (!linkedPeopleIds.isEmpty())
            throw new BadRequestException("Failure! People with ids " + StringUtils.prettyString(linkedPeopleIds) + " linked to this object.");
        super.delete(id);
    }

    public List<SelectResponse> getForSelect() {
        return em.createQuery("SELECT new ru.itmo.is.server.dto.response.SelectResponse(e.id, e.x, e.y) " +
                        "FROM Coordinates e ORDER BY e.x, e.y", SelectResponse.class)
                .getResultList();
    }

    public List<SelectResponse> getLinkedPeople(int id) {
        return em.createNamedQuery("Person.getLinkedToCoordId", SelectResponse.class)
                .setParameter("coordId", id).getResultList();
    }
}
