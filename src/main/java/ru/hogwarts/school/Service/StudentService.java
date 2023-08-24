package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
int count = 0;
    Object flag = new Object();
    public Student createStudent(Student student) {
        logger.info("Start createStudent");
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

    public List<String> getStudentSortedNameToUpperCase(Character letter){
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(e->e.charAt(0) == letter)
                .sorted()
                .collect(Collectors.toList());
    }
    public double getAverageAgeStudent(){
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average().orElse(0);
    }
    public int getSumAlgorithm(){
        int sum = Stream
                .iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        return sum;
    }
    public void threadUri(){
        int a = 0;
       List<Student> students = studentRepository.findAll();
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

       Thread thread = new Thread(() ->{
           System.out.println(students.get(2).getName());
           System.out.println(students.get(3).getName());
       });
      Thread thread2 = new Thread(() ->{
           System.out.println(students.get(4).getName());
           System.out.println(students.get(5).getName());
       });
      thread.start();
      thread2.start();
    }
    public void threadUriTwoSynchronized(){
       List<Student> students = studentRepository.findAll();
        printStud(students);
        printStud(students);
        Thread thread = new Thread(() ->{
            printStud(students);
            printStud(students);
        });
        Thread thread2 = new Thread(() ->{
            printStud(students);
            printStud(students);
        });


    }
    public void printStud(List<Student> students){
        synchronized (flag) {
            System.out.println(students.get(count).getName());
            count++;
        }
    }
}
