package ru.itmo.is.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class Coordinates extends AbstractEntity {

    @Column(nullable = false)
    private long x;

    @Min(-725)
    @NotNull
    private Double y; //Значение поля должно быть больше -725, Поле не может быть null
}