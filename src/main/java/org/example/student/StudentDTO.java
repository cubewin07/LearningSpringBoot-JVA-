package org.example.student;

public record StudentDTO (
        Long id,
        String fullName,
        String email,
        Integer age
){
}
