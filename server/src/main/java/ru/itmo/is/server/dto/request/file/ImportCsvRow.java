package ru.itmo.is.server.dto.request.file;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import ru.itmo.is.server.entity.*;
import ru.itmo.is.server.entity.security.User;

import java.util.Optional;

@Data
public class ImportCsvRow {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "eyeColor")
    private Color eyeColor;
    @CsvBindByName(column = "hairColor")
    private Color hairColor;
    @CsvBindByName(column = "height")
    private Float height;
    @CsvBindByName(column = "weight")
    private Long weight;
    @CsvBindByName(column = "passportId")
    private String passportId;
    @CsvBindByName(column = "nationality")
    private Country nationality;
    @CsvBindByName(column = "coordX")
    private Long coordX;
    @CsvBindByName(column = "coordY")
    private Double coordY;
    @CsvBindByName(column = "locationName")
    private String locationName;
    @CsvBindByName(column = "locationX")
    private Long locationX;
    @CsvBindByName(column = "locationY")
    private Double locationY;
    private User creator;

    public Optional<Coordinates> getCoordinates() {
        if (coordX == null || coordY == null) {
            return Optional.empty();
        }
        var c = new Coordinates();
        c.setX(coordX);
        c.setY(coordY);
        return Optional.of(c);
    }

    public Optional<Location> getLocation() {
        if (locationName == null || locationX == null || locationY == null) {
            return Optional.empty();
        }
        var l = new Location();
        l.setName(locationName);
        l.setX(locationX);
        l.setY(locationY);
        return Optional.of(l);
    }

    public Optional<Person> getPerson() {
        var cO = getCoordinates();
        if (cO.isEmpty() || name == null || eyeColor == null || hairColor == null
            || height == null || weight == null || passportId == null
        ) {
            return Optional.empty();
        }
        var p = new Person();
        p.setName(name);
        p.setEyeColor(eyeColor);
        p.setHairColor(hairColor);
        p.setHeight(height);
        p.setWeight(weight);
        p.setPassportId(passportId);
        p.setNationality(nationality);
        p.setCoordinates(cO.get());
        getLocation().ifPresent(p::setLocation);
        return Optional.of(p);
    }
}
