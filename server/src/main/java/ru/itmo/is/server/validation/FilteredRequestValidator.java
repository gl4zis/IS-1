package ru.itmo.is.server.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilteredRequestValidator implements ConstraintValidator<ValidFilteredRequest, FilteredRequest> {
    private static final Set<Class<?>> FILTER_ALLOWED_TYPES = Set.of(
            String.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class,
            int.class, long.class, float.class, double.class, boolean.class
    );
    private static final boolean IS_ENUM_FILTER_ALLOWED = true;
    private static final List<String> ADDITIONAL_FIELDS = List.of("id");

    @Override
    public boolean isValid(FilteredRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (value.getPaginator() == null || value.getFilters() == null || value.getSorter() == null) return false;
        var paginator = value.getPaginator();
        if (paginator.getPage() < 1 || paginator.getSize() < 0) return false;

        var fields = value.getEClass().getDeclaredFields();

        var sorter = value.getSorter();
        if (sorter.getField() == null || sorter.getType() == null) return false;
        var fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());
        fieldNames.addAll(ADDITIONAL_FIELDS);
        if (!fieldNames.contains(sorter.getField())) return false;

        var filters = value.getFilters();
        var filterFieldNames = Arrays.stream(fields)
                .filter(f -> FILTER_ALLOWED_TYPES.contains(f.getType()))
                .map(Field::getName).collect(Collectors.toSet());
        filterFieldNames.addAll(ADDITIONAL_FIELDS);
        if (IS_ENUM_FILTER_ALLOWED) {
            filterFieldNames.addAll(
                    Arrays.stream(fields)
                            .filter(f -> f.getType().isEnum())
                            .map(Field::getName).collect(Collectors.toSet())
            );
        }
        for (var field : filters.keySet()) {
            if (filters.get(field) == null ||filters.get(field).isEmpty()) return false;
            if (!filterFieldNames.contains(field)) return false;
        }
        return true;
    }
}
