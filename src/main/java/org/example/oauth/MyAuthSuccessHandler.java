package org.example.oauth;

import com.google.auth.oauth2.OAuth2CredentialsWithRefresh;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.config.JwtService;
import org.example.user_service.model.Role;
import org.example.user_service.model.User;
import org.example.user_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        if(userRepository.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .name(username)
                    .email(email)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }

        String jwt = jwtService.generateToken(email);

        // Send JWT as a JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        response.getWriter().flush();
    }
}
