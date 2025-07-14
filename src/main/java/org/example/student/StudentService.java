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
        if (StudentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student with email " + student.getEmail() + " already exists");
        }
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

    public void deleteStudentById(Long id) {
        StudentRepository.deleteById(id);
    }

}
