package org.example.authen;

public record CourseEnrollRequest(
        String email,
        Long courseId
)
{}
