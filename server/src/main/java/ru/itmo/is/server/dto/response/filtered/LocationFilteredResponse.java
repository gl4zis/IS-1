package ru.itmo.is.server.dto.response.filtered;

import ru.itmo.is.server.dto.response.LocationResponse;

import java.util.List;

public record LocationFilteredResponse(List<LocationResponse> locations, int count) {
}
