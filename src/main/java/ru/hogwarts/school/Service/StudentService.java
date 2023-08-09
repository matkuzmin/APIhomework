package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        if (studentRepository.findById(student.getId()) != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Collection<Student> filteringByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(ag -> ag.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findStudentsByFaculty(long id) {
        return studentRepository.findAllByFacultyId(id);
    }

    public Collection<Student> findAgeBetween(int min, int max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }
    public Integer AvgAgeStudent(){
        return studentRepository.AvgAgeStudent();
    }
    public Integer getCountStudent(){
        return studentRepository.getCountStudent();
    }
    public List<Student> getLastFiveStudent(){
        return studentRepository.getLastFiveStudent();
    }
}
