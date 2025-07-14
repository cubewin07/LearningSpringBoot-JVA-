package org.example.student;

public class StudentService {
    private final StudentRepository StudentRepository;

    public StudentService(StudentRepository StudentRepository) {
        this.StudentRepository = StudentRepository;
    }

    public Student addStudent(Student student) {
        return StudentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return StudentRepository.findById(id).orElseThrow();
    }

    public Object getStudentByFirstName(String firstName) {
        return StudentRepository.findByFirstName(firstName);
    }

    public Iterable<Student> getAllStudents() {
        return StudentRepository.findAll();
    }

}
