package org.example.notification.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.notification.ChatDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebsocketHandler extends TextWebSocketHandler {
    public Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String)session.getAttributes().get("userId");
        sessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatDTO chat = objectMapper.readValue(message.getPayload(), ChatDTO.class);
        if(sessions.get(chat.to()) != null) {
            sessions.get(chat.to()).sendMessage(new TextMessage(objectMapper.writeValueAsString(chat)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId = (String)session.getAttributes().get("userId");
        if (userId != null)
            sessions.remove(userId);

        //        can use closeStatus for logging
    }
}
