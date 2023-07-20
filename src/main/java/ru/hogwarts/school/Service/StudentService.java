package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Map<Long, Student> students = new HashMap<>();
    private long idCounter = 0;

    public Student createStudent(Student student) {
        student.setId(++idCounter);
       students.put(idCounter, student);
        return student;
    }

    public Student readStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }
    public Collection<Student> getAll(){
        return students.values();
    }

    public Collection<Student> filteringByAge(int age) {
       return students.values().stream()
                .filter(ag -> ag.getAge() == age)
                .collect(Collectors.toList());
    }
}
