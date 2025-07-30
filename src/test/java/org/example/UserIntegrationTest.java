package org.example;

import lombok.AllArgsConstructor;
import org.example.authen.AuthenService;
import org.example.authen.RegisterRequest;
import org.example.config.JwtService;
import org.example.course.CourseRepository;
import org.example.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenService authenService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    public void registerUserTesting() throws Exception {
        String requestBody = """
                {
                    "name": "Thang",
                    "password": "123456",
                    "email": "thang071208@gmail.com"
                }""";
        MvcResult res = mockMvc.perform(post("/api/v1/register")
                .contentType("application/json")
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String token = res.getResponse().getContentAsString();
        System.out.println(token);
    }

    @Test
    @Order(2)
    public void authenticateUser() throws Exception{
        String request = """
                    {
                        "email": "thang071208@gmail.com",
                        "password": "123456"
                    }
                """;
        MvcResult result = mockMvc.perform(post("/api/v1/authenticate")
                .contentType("application/json")
                .content(request)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        System.out.println(token);

    }

    @Test
    @Order(3)
    public void addingNewCourse() throws Exception{
        String request = """
                    {
                        "name": "Java",
                        "duration": 120
                    }
                """;
        MvcResult result = mockMvc.perform(post("/api/v1/course")
                .contentType("application/json")
                .content(request)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.duration").value("120 minutes"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }
}
