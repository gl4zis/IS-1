package ru.itmo.is.server.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import ru.itmo.is.server.dto.response.CoordResponse;
import ru.itmo.is.server.dto.response.LocationResponse;
import ru.itmo.is.server.dto.response.PersonResponse;

public abstract class WsJsonEncoder<E> implements Encoder.Text<E> {
    @Override
    public String encode(E res) throws EncodeException {
        try {
            return new ObjectMapper().writeValueAsString(res);
        } catch (JsonProcessingException e) {
            throw new EncodeException(e, "Cannot serialize response");
        }
    }

    static class Coord extends WsJsonEncoder<CoordResponse> {}
    static class Location extends WsJsonEncoder<LocationResponse> {}
    static class Person extends WsJsonEncoder<PersonResponse> {}
}
