package org.example.student;

import java.util.function.Function;

public class StudentDTOMapper implements Function<Student, StudentDTO> {
    @Override
    public StudentDTO apply(Student student) {
        return new StudentDTO(student.getId(), student.getFullName(), student.getEmail(), student.getAge());
    }
}
