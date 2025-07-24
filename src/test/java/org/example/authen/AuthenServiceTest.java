package org.example.authen;

import org.example.config.JwtService;
import org.example.user.Role;
import org.example.user.User;
import org.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthenServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenService authenService;

    @Test
    public void testRegisterUserReturnsToken() {

        RegisterRequest request = new RegisterRequest("test", "12344", "<EMAIL>");

        Mockito.when(userRepository.existsByEmail("<EMAIL>")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("12344")).thenReturn("12344");
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("mocked-token");

        var user = User.builder().name(request.name()).email(request.email()).password(request.password()).role(Role.USER).build();



        String token = authenService.registerUser(request).getToken();

        System.out.println(token);

        assertEquals("mocked-token", token);

    }
}