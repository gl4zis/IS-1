package ru.itmo.is.server.dto.request.filter;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.Coordinates;
import ru.itmo.is.server.entity.Location;
import ru.itmo.is.server.entity.Person;
import ru.itmo.is.server.entity.util.AbstractEntity;
import ru.itmo.is.server.validation.ValidFilteredRequest;

import java.util.Map;

@Getter
@Setter
@ValidFilteredRequest
public abstract class FilteredRequest {
    private final Class<? extends AbstractEntity> eClass;
    private Paginator paginator;
    private Sorter sorter;
    @Nullable
    private Map<String, String> filters;

    public FilteredRequest(Class<? extends AbstractEntity> eClass) {
        this.eClass = eClass;
    }

    public String toJPQL() {
        var queryBuilder = new StringBuilder().append("FROM ").append(eClass.getSimpleName());
        if (filters != null) {
            boolean hasWhere = false;
            for (var entry : filters.entrySet()) {
                if (hasWhere) queryBuilder.append(" AND ");
                else {
                    queryBuilder.append(" WHERE ");
                    hasWhere = true;
                }

                queryBuilder.append("LOWER(CAST(").append(entry.getKey()).append(" AS text))")
                        .append(" like '%").append(entry.getValue().toLowerCase()).append("%'");
            }
        }
        queryBuilder.append(" ").append(sorter.toSQL())
                .append(" ").append(paginator.toSQL());
        return queryBuilder.toString();
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
}
