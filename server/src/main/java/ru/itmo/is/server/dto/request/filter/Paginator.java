package ru.itmo.is.server.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paginator {
    private int page;
    private int size;

    public String toSQL() {
        return "LIMIT " + size + " OFFSET " + (page - 1) * size;
    }
}
