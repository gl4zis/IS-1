package ru.itmo.is.server.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import ru.itmo.is.server.dto.request.LocationRequest;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.dto.response.SelectResponse;
import ru.itmo.is.server.entity.Location;

import java.util.List;

@ApplicationScoped
public class LocationService extends BaseEntityService<Location, LocationRequest, LocationResponse> {

    public LocationService() {
        super(Location.class);
    }

    @Override
    @Transactional
    public void delete(int id) {
        var linkedPeopleIds = getLinkedPeople(id).stream().map(SelectResponse::getId).toList();
        if (!linkedPeopleIds.isEmpty())
            throw new BadRequestException("Failure! People with ids [" +
                    StringUtils.join(linkedPeopleIds, ",") +
                    "] linked to this object.");
        super.delete(id);
    }

    public List<SelectResponse> getLinkedPeople(int id) {
        return em.createNamedQuery("Person.getLinkedToLocationId", SelectResponse.class)
                .setParameter("locationId", id).getResultList();
    }
}
