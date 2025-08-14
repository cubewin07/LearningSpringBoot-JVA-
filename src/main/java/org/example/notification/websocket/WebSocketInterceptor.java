package org.example.notification.websocket;

import lombok.RequiredArgsConstructor;
import org.example.Exception.UsernameNotFound;
import org.example.user_service.model.User;
import org.example.user_service.repository.UserRepository;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {
    private final UserRepository userRepository;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String param = request.getURI().getQuery();
        if(param != null && param.contains("username=")) {
            String username = param.substring(param.indexOf("username=") + 9);
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFound("User not found"));
            attributes.put("userId", user.getId());
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
