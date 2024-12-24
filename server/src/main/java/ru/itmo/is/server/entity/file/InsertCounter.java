package ru.itmo.is.server.entity.file;

import lombok.Getter;

@Getter
public class InsertCounter {
    private int insertedPeople;
    private int insertedCoordinates;
    private int insertedLocation;

    public void incPerson() {
        insertedPeople++;
    }

    public void incCoordinates() {
        insertedCoordinates++;
    }

    public void incLocation() {
        insertedLocation++;
    }
}
