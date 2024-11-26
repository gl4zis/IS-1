package ru.itmo.is.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.util.AbstractEntity;

@Entity
@Getter
@Setter
@NamedQuery(name = "Coordinates.findAll", query = "FROM Coordinates")
@NamedQuery(name = "Coordinates.count", query = "SELECT COUNT(l) FROM Coordinates l")
public class Coordinates extends AbstractEntity implements Comparable<Coordinates> {

    @Column(nullable = false)
    private long x;

    @NotNull
    @Min(-725)
    private Double y; //Значение поля должно быть больше -725, Поле не может быть null

    @Override
    public int compareTo(Coordinates o) {
        return Double.compare(zeroDistance(), o.zeroDistance());
    }

    private double zeroDistance() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}