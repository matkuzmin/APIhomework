package ru.hogwarts.school.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Service.FacultyService;

import java.util.ArrayList;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
return facultyService.createFaculty(faculty);
    }
@GetMapping("{id}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if(faculty == null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
@PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyService.updateFaculty(faculty);
        if(faculty1 == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }
@DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
    }
    @GetMapping("/sorted/{color}")
    public ArrayList<Faculty> filteringByAge(@PathVariable int color){
        return facultyService.filteringByAge(color);
    }
}
