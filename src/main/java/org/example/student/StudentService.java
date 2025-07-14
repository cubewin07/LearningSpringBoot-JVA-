package org.example.student;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository StudentRepository;

    public StudentService(StudentRepository StudentRepository) {
        this.StudentRepository = StudentRepository;
    }

    public Student addStudent(Student student) {
        return StudentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return StudentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found"));
    }

    public List<Student> getStudentByFirstName(String firstName) {
        return StudentRepository.findByFirstName(firstName);
    }

    public Iterable<Student> getAllStudents() {
        return StudentRepository.findAll();
    }

}
