package ru.itmo.is.server.dto.request.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.entity.file.FileImport;
import ru.itmo.is.server.validation.ValidFilteredRequest;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ValidFilteredRequest
@ToString
public abstract class FilteredRequest {
    private final Class<?> eClass;
    private Paginator paginator = new Paginator();
    private Sorter sorter = new Sorter();
    private Map<String, String> filters = Map.of();

    public FilteredRequest(Class<?> eClass) {
        this.eClass = eClass;
    }

    public String toJPQL() {
        var queryBuilder = new StringBuilder().append("FROM ").append(eClass.getSimpleName());
        if (filters != null) {
            filtersToJPQL(filters, queryBuilder);
        }
        queryBuilder.append(" ").append(sorter.toSQL())
                .append(" ").append(paginator.toSQL());
        return queryBuilder.toString();
    }

    public void addCustomFilter(String key, String substringKey) {
        filters.put(key, substringKey);
    }

    public static void filtersToJPQL(Map<String, String> filters, StringBuilder builder) {
        boolean hasWhere = false;
        for (var entry : filters.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isBlank()) continue;
            if (hasWhere) builder.append(" AND ");
            else {
                builder.append(" WHERE ");
                hasWhere = true;
            }

            builder.append("LOWER(CAST(").append(entry.getKey()).append(" AS text))")
                    .append(" like '%").append(entry.getValue().trim().toLowerCase()).append("%'");
        }
    }

    public static class FilteredCoordRequest extends FilteredRequest {
        public FilteredCoordRequest() {
            super(Coordinates.class);
        }
    }

    public static class FilteredLocationRequest extends FilteredRequest {
        public FilteredLocationRequest() {
            super(Location.class);
        }
    }

    public static class FilteredPersonRequest extends FilteredRequest {
        public FilteredPersonRequest() {
            super(Person.class);
        }
    }

    public static class FilteredImportRequest extends FilteredRequest {
        public FilteredImportRequest() {
            super(FileImport.class);
        }
    }
}
