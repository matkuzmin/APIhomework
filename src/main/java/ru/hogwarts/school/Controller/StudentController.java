package ru.hogwarts.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.Collection;

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
    public ResponseEntity<Student> readStudent(@PathVariable long id) {
    Student student = studentService.readStudent(id);

    return ResponseEntity.ok(student);}

    @GetMapping
    public Collection<Student> getall(){
        return studentService.getAll();
    }
@PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
    Student student1 = studentService.updateStudent(student);
    if (student1 == null){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(student1);
    }
@DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
         studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/filter/{age}")
    public Collection<Student> filteringByAge(@PathVariable int age){
        return studentService.filteringByAge(age);
    }
}
