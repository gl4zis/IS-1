package ru.itmo.is.server.ws;

import jakarta.websocket.Encoder;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
@Getter
public enum SubscriptionType {
    COORD("coord", WsJsonEncoder.Coord.class),
    LOCATION("location", WsJsonEncoder.Location.class),
    PERSON("person", WsJsonEncoder.Person.class),;

    private final String path;
    private final Class<? extends Encoder.Text<?>> encoderClass;
    private final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public static SubscriptionType fromPath(String path) {
        for (var type : SubscriptionType.values()) {
            if (type.path.equals(path)) return type;
        }
        return null;
    }
}
