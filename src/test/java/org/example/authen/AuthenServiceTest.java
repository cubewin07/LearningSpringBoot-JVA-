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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthenServiceTest {

    @Mock
    private UserRepository userRepository;

//    @Spy
//    private JwtService jwtService = Mockito.spy(new JwtService());

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenService authenService;

    @Test
    public void testRegisterUserReturnsToken() {
        RegisterRequest request = new RegisterRequest("test", "test", "<EMAIL>");
        Mockito.when(userRepository.findByEmail("<EMAIL>")).thenReturn(Optional.empty());

        var user = User.builder().name(request.name()).email(request.email()).password(request.password()).role(Role.USER).build();



//        doReturn("token").when(jwtService).generateToken(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("mock-token");

        String token = authenService.registerUser(request).getToken();



        assertNotNull(token);
        assertEquals("mock-token", token);
    }
}