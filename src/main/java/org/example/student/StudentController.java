package org.example.student;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository StudentRepository;

    public StudentController(StudentRepository StudentRepository) {
        this.StudentRepository = StudentRepository;
    }


    @PostMapping()
    public Student addStudent(@RequestBody Student student) {
        return StudentRepository.save(student);
    }

    @GetMapping("/{id}") // Final URL = /student/1
    public Student getStudentById(@PathVariable("id") Long id) {
        return StudentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found"));
    }

    @GetMapping()
    public Object getStudentByFirstName(@RequestParam(required = false) String firstName) {
        if (firstName != null) {
            return StudentRepository.findByFirstName(firstName);
        }
        return StudentRepository.findAll();
    }
}
