package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import ru.itmo.is.server.dto.request.CoordRequest;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.mapper.CoordMapper;
import ru.itmo.is.server.mapper.PersonMapper;
import ru.itmo.is.server.utils.StringUtils;

import java.util.List;

@ApplicationScoped
public class CoordService extends BaseEntityService<Coordinates, CoordRequest, CoordResponse, CoordMapper> {
    @Inject
    private PersonMapper personMapper;

    public CoordService() {
        super(Coordinates.class);
    }

    @Override
    @Transactional
    public void delete(int id) {
        var linkedPeopleIds = getLinkedPeopleIds(id);
        if (!linkedPeopleIds.isEmpty())
            throw new BadRequestException("Failure! People with ids " + StringUtils.prettyString(linkedPeopleIds) + " linked to this object.");
        em.remove(find(id));
    }

    public List<Integer> getLinkedPeopleIds(int id) {
        return em.createNamedQuery("Person.getIdsLinkedToCoordId", Integer.class)
                .setParameter("coordId", id).getResultList();
    }

    public List<PersonResponse> getLinkedPeople(int id) {
        return personMapper.toDto(em.createNamedQuery("Person.getLinkedToCoordId", Person.class)
                .setParameter("coordId", id).getResultList());
    }
}
