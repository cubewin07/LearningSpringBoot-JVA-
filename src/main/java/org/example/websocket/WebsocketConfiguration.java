package org.example.websocket;

import lombok.AllArgsConstructor;
import org.example.user_service.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebsocketConfiguration implements WebSocketConfigurer {
    private UserRepository userRepository;
    private WebsocketHandler websocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(websocketHandler, "/ws")
                .addInterceptors(new WebSocketInterceptor(userRepository))
                .setAllowedOrigins("*");
    }
}
