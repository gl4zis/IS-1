package ru.itmo.is.server.ws;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{type}", encoders = {
        WsJsonEncoder.Coord.class,
        WsJsonEncoder.Location.class,
        WsJsonEncoder.Person.class
})
public class WsSubscription {
    private static final Map<String, SubscriptionType> SUBSCRIPTIONS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("type") String path) {
        var type = SubscriptionType.fromPath(path);
        try {
            if (type == null) {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Invalid subscription type"));
            } else {
                type.getSessions().add(session);
                SUBSCRIPTIONS.put(session.getId(), type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        var type = SUBSCRIPTIONS.remove(session.getId());
        if (type != null) type.getSessions().remove(session);
    }

    public static void onUpdate(SubscriptionType type, List<?> entities) {
        for (var s : type.getSessions()) {
            try {
                if (s.isOpen()) s.getBasicRemote().sendObject(entities);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
}
