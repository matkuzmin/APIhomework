package ru.hogwarts.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student readStudent(@PathVariable long id) {
        Student student = studentService.readStudent(id);
        return student;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student student1 = studentService.updateStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public Collection<Student> findStudentsAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findAgeBetween(min, max);
    }

    @GetMapping("/faculty/{id}")
    public Collection<Student> findStudentsByFaculty(@PathVariable Long id) {
        return studentService.findStudentsByFaculty(id);
    }

    @GetMapping("/filter/{age}")
    public Collection<Student> filteringByAge(@PathVariable int age) {
        return studentService.filteringByAge(age);
    }

    @GetMapping("/avg-age")
    public Integer findAvgAgeStudent() {
        return studentService.AvgAgeStudent();
    }

    @GetMapping("/student-count")
    public Integer getCountStudent() {
        return studentService.getCountStudent();
    }

    @GetMapping("/last-five-Student")
    public List<Student> getLastFiveStudent() {
        return studentService.getLastFiveStudent();
    }

    @GetMapping("/findStudentByLetter/{letter}")
    public List<String> getStudentSortedNameUpperCase(@PathVariable Character letter) {
        return studentService.getStudentSortedNameToUpperCase(letter);
    }

    @GetMapping("/student-AverageAge")
    public double getAverageAgeStudent() {
        return studentService.getAverageAgeStudent();
    }

    @GetMapping("/getSumAlgorithm")
    public int getSumAlgorithm(){
       return studentService.getSumAlgorithm();
    }
}
