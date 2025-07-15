package org.example.student;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService StudentService;

    public StudentController(StudentService StudentService) {
        this.StudentService = StudentService;
    }


    @PostMapping()
    public Student addStudent( @Valid @RequestBody StudentRequest request) {

        Student student = new Student(request.getFirstName(), request.getLastName(), request.getEmail(), request.getAge());

        return StudentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable("id") Long id) {
        return StudentService.getStudentById(id);
    }

    @GetMapping()
    public Object getStudentByFirstName(@RequestParam(required = false) String firstName) {
        if (firstName != null) {
            List<Student> StudentListByFirstName = StudentService.getStudentByFirstName(firstName);
            if (StudentListByFirstName.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No student found with name " + firstName);
            }
            return StudentListByFirstName;
        }
        return StudentService.getAllStudents();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return StudentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") Long id) {
        StudentService.deleteStudentById(id);
    }
}
