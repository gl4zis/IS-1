package ru.itmo.is.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.server.entity.util.AbstractEntity;

@Entity
@Getter
@Setter
public class Location extends AbstractEntity {

    @NotNull
    private Long x; //Поле не может быть null

    @NotNull
    private Double y; //Поле не может быть null

    @NotNull
    @Length(max = 312)
    private String name; //Длина строки не должна быть больше 312, Поле не может быть null
}
