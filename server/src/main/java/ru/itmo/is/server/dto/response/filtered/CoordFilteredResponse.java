package ru.itmo.is.server.dto.response.filtered;

import ru.itmo.is.server.dto.response.CoordResponse;

import java.util.List;

public record CoordFilteredResponse(List<CoordResponse> coordinates, int count) {
}
