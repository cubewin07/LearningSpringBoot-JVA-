package org.example;

import lombok.AllArgsConstructor;
import org.example.authen.AuthenService;
import org.example.authen.RegisterRequest;
import org.example.config.JwtService;
import org.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenService authenService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void registerUserTesting() throws Exception {
        String requestBody = """
                {
                    "name": "Thang",
                    "password": "12334",
                    "email": "thang071208@gmail.com"
                }""";
        var res = mockMvc.perform(post("/api/v1/register")
                .contentType("application/json")
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = res.getResponse().getContentAsString();
        System.out.println(token);
    }
}
