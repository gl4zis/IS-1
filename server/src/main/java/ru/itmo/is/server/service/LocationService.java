package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.dto.response.SelectResponse;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.utils.StringUtils;

import java.util.List;

@ApplicationScoped
public class LocationService extends BaseEntityService<Location, LocationRequest, LocationResponse> {

    public LocationService() {
        super(Location.class);
    }

    @Override
    public void validate(Location location) {
        if (!isNameUnique(location.getName())) {
            throw new BadRequestException("Location with name " + location.getName() + " already exists");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        var linkedPeopleIds = getLinkedPeople(id).stream().map(SelectResponse::getId).toList();
        if (!linkedPeopleIds.isEmpty())
            throw new BadRequestException("Failure! People with ids " + StringUtils.prettyString(linkedPeopleIds) + " linked to this object.");
        super.delete(id);
    }

    public List<SelectResponse> getLinkedPeople(int id) {
        return em.createNamedQuery("Person.getLinkedToLocationId", SelectResponse.class)
                .setParameter("locationId", id).getResultList();
    }

    public boolean isNameUnique(String name) {
        return em.createNamedQuery("Location.isNameUnique", boolean.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
