package ru.itmo.is.server.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class FilteredRequestValidator implements ConstraintValidator<ValidFilteredRequest, FilteredRequest> {
    private static final Set<Class<?>> FILTER_ALLOWED_TYPES = Set.of(
            String.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class,
            int.class, long.class, float.class, double.class, boolean.class
    );
    private static final boolean IS_ENUM_FILTER_ALLOWED = true;
    private static final List<String> ADDITIONAL_FIELDS = List.of("id");
    // Костыль жесткий, но времени нет
    private static final Map<String, String> MAPPING_FIELDS = Map.of(
            "locationId", "location.id",
            "coordinatesId", "coordinates.id"
    );

    @Override
    public boolean isValid(FilteredRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (value.getPaginator() == null || value.getSorter() == null || value.getFilters() == null) return false;
        var paginator = value.getPaginator();
        if (paginator.getPage() < 1 || paginator.getSize() < 0) return false;

        var sorter = value.getSorter();
        if (sorter.getField() == null || sorter.getType() == null) return false;
        var fields = value.getEClass().getDeclaredFields();
        var fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());
        fieldNames.addAll(ADDITIONAL_FIELDS);
        if (!fieldNames.contains(sorter.getField())) return false;

        var filters = value.getFilters();
        if (filters.isEmpty()) return true;

        var filterFields = new HashSet<String>();
        for (var entry : filters.entrySet()) {
            filterFields.add(MAPPING_FIELDS.getOrDefault(entry.getKey(), entry.getKey()));
        }

        var filterFieldNames = Arrays.stream(fields)
                .filter(f -> FILTER_ALLOWED_TYPES.contains(f.getType()))
                .map(Field::getName).collect(Collectors.toSet());
        filterFieldNames.addAll(MAPPING_FIELDS.values());
        filterFieldNames.addAll(ADDITIONAL_FIELDS);

        if (IS_ENUM_FILTER_ALLOWED) {
            filterFieldNames.addAll(
                    Arrays.stream(fields)
                            .filter(f -> f.getType().isEnum())
                            .map(Field::getName).collect(Collectors.toSet())
            );
        }
        return filterFieldNames.containsAll(filterFields);
    }
}
