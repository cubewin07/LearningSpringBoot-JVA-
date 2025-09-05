package org.example;

import org.example.user_service.service.AuthenService;
import org.example.config.JwtService;
import org.example.course.CourseRepository;
import org.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnableCaching
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

    private static String jwtToken;
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
        String responseBody = result.getResponse().getContentAsString();
        jwtToken = responseBody.substring(responseBody.indexOf(":\"") + 2, responseBody.lastIndexOf("\""));
        System.out.println(responseBody);

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

    @Test
    @Order(4)
    public void enrollCourse() throws Exception{
        String request = """
                    {
                        "email": "thang071208@gmail.com",
                        "courseId": 1
                    }
                    """;
        MvcResult result = mockMvc.perform(post("/api/v1/enrollCourse")
                .contentType("application/json")
                .content(request)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.description").value("120 minutes"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    public void getUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/own")
                .header("Authorization", "Bearer " + jwtToken)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Thang"))
                .andExpect(jsonPath("$.email").value("thang071208@gmail.com"))
                .andExpect(jsonPath("$.courses").exists())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


}
