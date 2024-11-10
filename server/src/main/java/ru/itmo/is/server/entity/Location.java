package ru.itmo.is.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "location")
@Getter
@Setter
public class Location extends AbstractEntity {

    @NotNull
    @Column(name = "x")
    private Long x; //Поле не может быть null

    @NotNull
    @Column(name = "y")
    private Double y; //Поле не может быть null

    @NotNull
    @Length(max = 312)
    @Column(name = "name", length = 312)
    private String name; //Длина строки не должна быть больше 312, Поле не может быть null
}
