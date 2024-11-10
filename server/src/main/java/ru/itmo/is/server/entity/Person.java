package ru.itmo.is.server.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    @Column(name = "eye_color")
    private Color eyeColor; //Поле не может быть null

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private Color hairColor; //Поле не может быть null

    @Nullable
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //Поле может быть null

    @Positive
    @Column(name = "height", nullable = false)
    private float height; //Значение поля должно быть больше 0

    @NotNull
    @Min(1)
    @Column(name = "weight")
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0

    @NotNull
    @Length(min = 4)
    @Column(name = "passport_id")
    private String passportId; //Длина строки должна быть не меньше 4, Поле не может быть null

    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Country nationality; //Поле может быть null
}
