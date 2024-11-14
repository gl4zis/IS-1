package ru.itmo.is.server.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itmo.is.server.dto.request.PersonRequest;

public class PersonRequestValidator implements ConstraintValidator<ValidPerson, PersonRequest> {
    @Override
    public boolean isValid(PersonRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;

        boolean coordIdNotNull = value.getCoordId() != null;
        boolean coordNotNull = value.getCoord() != null;
        boolean locationIdNull = value.getLocationId() == null;
        boolean locationNull = value.getLocation() == null;
        return (coordIdNotNull ^ coordNotNull) && (locationIdNull || locationNull);
    }
}
