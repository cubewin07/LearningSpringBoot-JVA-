package org.example.authen;

import org.example.user.Role;
import org.example.user.User;
import org.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenService authenService;

    @Test
    public void testRegisterUserReturnsToken() {
        Mockito.when(userRepository.findByEmail("<EMAIL>")).thenReturn(Optional.empty());

        var request = new RegisterRequest("test", "test", "<EMAIL>");
        String token = authenService.registerUser(request);

        assertNotNull(token);
        assertTrue(token.length() > 0 || token.startsWith("Bearer "));
    }
}