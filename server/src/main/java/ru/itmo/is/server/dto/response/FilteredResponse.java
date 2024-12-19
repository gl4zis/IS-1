package ru.itmo.is.server.dto.response;

import lombok.Value;

import java.util.List;

@Value
public class FilteredResponse<RES> {
    List<RES> data;
    int count;
}
