package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info(("Start createStudent");
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {
        logger.info("Start readStudent");
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        logger.info("Start updateStudent");
        if (studentRepository.findById(student.getId()) != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) {
        logger.info("Start deleteStudent");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("Start getAll");
        return studentRepository.findAll();
    }

    public Collection<Student> filteringByAge(int age) {
        logger.info("Start filteringByAge");
        return studentRepository.findAll().stream()
                .filter(ag -> ag.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findStudentsByFaculty(long id) {
        logger.info("Start findStudentsByFaculty");
        return studentRepository.findAllByFacultyId(id);
    }

    public Collection<Student> findAgeBetween(int min, int max) {
        logger.info("Start findAgeBetween");
        return studentRepository.findStudentByAgeBetween(min, max);
    }
    public Integer AvgAgeStudent(){
        logger.info("Start AvgAgeStudent");
        return studentRepository.AvgAgeStudent();
    }
    public Integer getCountStudent(){
        logger.info("Start getCountStudent");
        return studentRepository.getCountStudent();
    }
    public List<Student> getLastFiveStudent(){
        logger.info("Start getLastFiveStudent");
        return studentRepository.getLastFiveStudent();
    }
}
