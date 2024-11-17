package ru.itmo.is.server.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sorter {
    private String field;
    private Type type;

    public String toSQL() {
        return "ORDER BY " + field + " " + type.name();
    }

    public enum Type {
        ASC,
        DESC
    }
}
