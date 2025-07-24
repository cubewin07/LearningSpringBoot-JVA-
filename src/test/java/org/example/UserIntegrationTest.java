package org.example;

import lombok.AllArgsConstructor;
import org.example.authen.AuthenService;
import org.example.authen.RegisterRequest;
import org.example.config.JwtService;
import org.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AllArgsConstructor
@AutoConfigureMockMvc
public class UserIntegrationTest {

    private UserRepository userRepository;
    private AuthenService authenService;
    private JwtService jwtService;

    private MockMvc mockMvc;


    @Test
    public void registerUserTesting() throws Exception {
        String requestBody = "{\n" +
                "    \"name\": \"Thang\",\n" +
                "    \"password\": \"12334\",\n" +
                "    \"email\": \"thang@gmail.com\"\n" +
                "}";
        mockMvc.perform(post("/api/v1/register")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk());

    }
}
