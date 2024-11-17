package ru.itmo.is.server.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itmo.is.server.dto.request.filter.FilteredRequest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FilteredRequestValidator implements ConstraintValidator<ValidFilteredRequest, FilteredRequest> {
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
        fieldNames.add("id");
        if (!fieldNames.contains(sorter.getField())) return false;

        var filters = value.getFilters();
        var stringFieldNames = Arrays.stream(fields)
                .filter(f -> f.getType() == String.class || f.getType().isEnum())
                .map(Field::getName).collect(Collectors.toSet());
        for (var f : filters) {
            if (f.getValue() == null || f.getValue().isEmpty()) return false;
            if (!stringFieldNames.contains(f.getKey())) return false;
        }

        return true;
    }
}
