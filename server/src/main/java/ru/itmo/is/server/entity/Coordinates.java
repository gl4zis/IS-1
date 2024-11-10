package ru.itmo.is.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.util.AbstractEntity;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Coordinates extends AbstractEntity {

    @Column(nullable = false)
    private long x;

    @NotNull
    @Min(-725)
    private Double y; //Значение поля должно быть больше -725, Поле не может быть null
}