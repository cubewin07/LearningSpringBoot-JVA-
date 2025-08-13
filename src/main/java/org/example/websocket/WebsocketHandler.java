package org.example.websocket;

import lombok.AllArgsConstructor;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketHandler extends TextWebSocketHandler {
    public Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String)session.getAttributes().get("userId");
        sessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId = (String)session.getAttributes().get("userId");
        if (userId != null)
            sessions.remove(userId);

        //        can use closeStatus for logging
    }
}
