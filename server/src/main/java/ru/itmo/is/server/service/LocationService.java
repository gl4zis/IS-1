package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.dto.response.PersonResponse;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.mapper.PersonMapper;
import ru.itmo.is.server.utils.StringUtils;
import ru.itmo.is.server.ws.SubscriptionType;
import ru.itmo.is.server.ws.WsSubscription;

import java.util.List;

@ApplicationScoped
public class LocationService extends BaseEntityService<Location, LocationRequest, LocationResponse> {
    @Inject
    private PersonMapper personMapper;

    public LocationService() {
        super(Location.class);
    }

    @Override
    @Transactional
    public void create(LocationRequest req) {
        em.persist(mapper.toEntity(req));
        WsSubscription.onUpdate(SubscriptionType.LOCATION, getAll());
    }

    @Override
    @Transactional
    public void update(int id, LocationRequest req) {
        em.merge(mapper.toEntity(req, find(id)));
        WsSubscription.onUpdate(SubscriptionType.LOCATION, getAll());
    }

    @Override
    @Transactional
    public void delete(int id) {
        var linkedPeopleIds = getLinkedPeopleIds(id);
        if (!linkedPeopleIds.isEmpty())
            throw new BadRequestException("Failure! People with ids " + StringUtils.prettyString(linkedPeopleIds) + " linked to this object.");
        em.remove(find(id));
        WsSubscription.onUpdate(SubscriptionType.LOCATION, getAll());
    }

    public List<Integer> getLinkedPeopleIds(int locationId) {
        return em.createNamedQuery("Person.getIdsLinkedToLocationId", Integer.class)
                .setParameter("locationId", locationId).getResultList();
    }

    public List<PersonResponse> getLinkedPeople(int locationId) {
        return personMapper.toDto(em.createNamedQuery("Person.getLinkedToLocationId", Person.class)
                .setParameter("locationId", locationId).getResultList());
    }
}
