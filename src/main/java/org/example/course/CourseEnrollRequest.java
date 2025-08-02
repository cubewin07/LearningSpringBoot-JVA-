package org.example.course;

public record CourseEnrollRequest(
        String email,
        Long courseId
)
{}
