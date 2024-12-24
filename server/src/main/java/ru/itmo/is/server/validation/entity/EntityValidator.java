package ru.itmo.is.server.validation.entity;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.itmo.is.server.entity.util.AbstractEntity;

public abstract class EntityValidator<E extends AbstractEntity> {
    private final Validator validator;

    public EntityValidator() {
        var factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validate(E entity) {
        var errors = validator.validate(entity);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
