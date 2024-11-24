package ru.itmo.is.server.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

import static ru.itmo.is.server.dto.request.filter.Sorter.Type.ASC;

@Getter
@Setter
public class Sorter {
    private String field = "id";
    private Type type = ASC;

    public String toSQL() {
        return "ORDER BY " + field + " " + type.name();
    }

    public enum Type {
        ASC,
        DESC
    }
}
