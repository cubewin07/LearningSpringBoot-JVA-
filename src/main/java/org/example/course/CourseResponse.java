package org.example.course;

import lombok.Builder;

@Builder
public record CourseResponse(
        Long courseId,
        String name,
        String description
)
{}
