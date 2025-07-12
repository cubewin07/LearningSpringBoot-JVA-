package org.example.student;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public List<Student> getStudent() {
        return StudentRepository.findAll();
    }

}
