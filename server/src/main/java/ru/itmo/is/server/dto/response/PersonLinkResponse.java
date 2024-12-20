package ru.itmo.is.server.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonLinkResponse extends SelectResponse {
    private Integer coordId;
    private Integer locationId;

    public PersonLinkResponse(Integer id, String name, Integer coordId, Integer locationId) {
        super(id, name);
        this.coordId = coordId;
        this.locationId = locationId;
    }
}
