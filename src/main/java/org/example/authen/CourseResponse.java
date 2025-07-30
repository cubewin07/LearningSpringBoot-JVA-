package org.example.authen;

import lombok.Builder;

@Builder
public record CourseResponse(
        Long courseId,
        String name,
        String description
)
{}
