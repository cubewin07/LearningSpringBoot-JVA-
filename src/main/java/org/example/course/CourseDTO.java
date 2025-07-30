package org.example.course;

import lombok.Builder;

@Builder
public record CourseDTO(
        Long id,
        String name,
        String duration
)
{}
