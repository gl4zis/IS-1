package ru.itmo.is.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.server.entity.util.AbstractEntity;

@Entity
@Table(name = "person")
@Getter
@Setter
@NamedQuery(name = "Person.findAll", query = "FROM Person")
@NamedQuery(name = "Person.count", query = "SELECT COUNT(p) FROM Person p")
@NamedQuery(name = "Person.countByWeight", query = "SELECT COUNT(p) FROM Person p WHERE p.weight = :weight")
@NamedQuery(name = "Person.countByEyeColor", query = "SELECT COUNT(p) FROM Person p WHERE p.eyeColor = :eyeColor")
public class Person extends AbstractEntity {

    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    @ManyToOne
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "eye_color")
    private Color eyeColor; //Поле не может быть null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private Color hairColor; //Поле не может быть null

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //Поле может быть null

    @Positive
    @Column(nullable = false)
    private float height; //Значение поля должно быть больше 0

    @NotNull
    @Min(1)
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0

    @NotNull
    @Length(min = 4)
    @Column(name = "passport_id")
    private String passportId; //Длина строки должна быть не меньше 4, Поле не может быть null

    @Enumerated(EnumType.STRING)
    private Country nationality; //Поле может быть null
}
