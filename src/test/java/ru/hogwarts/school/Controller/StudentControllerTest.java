package ru.hogwarts.school.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.AvatarService;
import ru.hogwarts.school.Service.FacultyService;
import ru.hogwarts.school.Service.StudentService;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private StudentController studentController;

    final Long id = 1L;
    final String name = "Dima";
    final int age = 23;

    @Test
    void saveStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void printStudentsTest() throws Exception {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void findStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void putStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getIdStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void StudentsFilterAgeTest() throws Exception {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        Student student1 = new Student();
        student1.setName("name");
        student1.setAge(age);
        student1.setId(2);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter/" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void StudentsFilterMinAndMaxAgeTest() throws Exception {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        Student student1 = new Student();
        student1.setName("name");
        student1.setAge(age);
        student1.setId(2);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        when(studentRepository.findStudentByAgeBetween(10, 30)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?min=10&max=30")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void StudentsFilterFacultyTest() throws Exception {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JSONObject facultyObject = new JSONObject();
//            facultyObject.put("name", "");
//            facultyObject.put("age", "");
        Faculty faculty = new Faculty();
        faculty.setId(1);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        student.setFaculty(faculty);
        Student student1 = new Student();
        student1.setName("name");
        student1.setAge(age);
        student1.setId(2);
        student1.setFaculty(faculty);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        when(studentRepository.findAllByFacultyId(1L)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/1")
//                            .content(facultyObject.toString())
//                            .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                    .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student, student1))));
    }
}


