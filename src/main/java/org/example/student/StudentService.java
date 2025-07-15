package org.example.student;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository StudentRepository;
    private final StudentDTOMapper StudentDTOMapper;

    public StudentService(StudentRepository StudentRepository, StudentDTOMapper studentDTOMapper) {
        this.StudentRepository = StudentRepository;
        StudentDTOMapper = studentDTOMapper;
    }

    public Student addStudent(Student student) {
        if (StudentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student with email " + student.getEmail() + " already exists");
        }
        return StudentRepository.save(student);
    }

    public StudentDTO getStudentById(Long id) {
        Optional<Student> student = StudentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
        } else {
            return new StudentDTO(student.get().getId(), student.get().getFullName(), student.get().getEmail(), student.get().getAge());
        }
    }

    public List<Student> getStudentByFirstName(String firstName) {
        return StudentRepository.findByFirstName(firstName);
    }

    public Iterable<StudentDTO> getAllStudents() {

        return
                StudentRepository.findAll()
                        .stream()
                        .map(StudentDTOMapper)
                        .collect(Collectors.toList());
    }

    public void deleteStudentById(Long id) {
        if (!StudentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
        }
        StudentRepository.deleteById(id);
    }

    @Transactional
    public Student updateStudent(Long id, Student student) {
        Student studentToUpdate = StudentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found"));
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setEmail(student.getEmail());
        studentToUpdate.setAge(student.getAge());
        return studentToUpdate;
    }


}
