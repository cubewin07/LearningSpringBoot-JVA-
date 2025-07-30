package org.example.user;

import lombok.Builder;
import org.example.course.CourseDTO;

import java.util.List;

@Builder
public record UserDTO(
        Long id,
        String name,
        String email,
        Role role,
        List<CourseDTO> courses
)
{}
