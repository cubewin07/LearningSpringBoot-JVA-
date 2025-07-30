package org.example.user;

import org.example.course.CourseDTO;

import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        Role role,
        List<CourseDTO> courses
)
{}
