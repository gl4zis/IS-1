package ru.itmo.is.server.dto.response.filtered;

import ru.itmo.is.server.dto.response.PersonResponse;

import java.util.List;

public record PersonFilteredResponse(List<PersonResponse> people, int count) {
}
