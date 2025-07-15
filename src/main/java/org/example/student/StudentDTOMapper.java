package org.example.student;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<Student, StudentDTO> {
    @Override
    public StudentDTO apply(Student student) {
        return new StudentDTO(student.getId(), student.getFullName(), student.getEmail(), student.getAge());
    }
}
