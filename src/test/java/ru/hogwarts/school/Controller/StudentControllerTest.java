package ru.hogwarts.school.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    private long findLastStudentId() {
        List<Student> students = studentRepository.findAll();
        Student lastStudent = students.stream()
                .max(Comparator.comparing(Student::getId))
                .orElse(null);
        if (lastStudent == null) {
            throw new NullPointerException("таблица со студентами пуста");
        }
        return lastStudent.getId();
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testReadStudent() throws Exception {
        Student actual = studentRepository.findById(3L).get();
        Student result = testRestTemplate.getForObject("http://localhost:" + port + "/student/" + 3, Student.class);
        Assertions.assertThat(result.getName()).isEqualTo(actual.getName());
        Assertions.assertThat(result.getAge()).isEqualTo(actual.getAge());
    }

    @Test
    void testReadStudents() throws Exception {
        Assertions.assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setAge(10);
        student.setName("Dima");
        Student result = testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(result.getAge()).isEqualTo(10);
        Assertions.assertThat(result.getName()).isEqualTo("Dima");
    }

    @Test
    void testPutStudent() throws Exception {
        Student student = new Student();
        student.setAge(10);
        student.setName("Dima");
        Student result = testRestTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        result.setName("Harry");
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(result),
                Student.class);

        Assertions.assertThat(studentResponseEntity.getBody().getName()).isEqualTo("Harry");
        Assertions.assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testFilterAgeStudent() throws Exception {

        String result = testRestTemplate.getForObject("http://localhost:" + port + "/student/filter/200", String.class);
        ResponseEntity<String> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/filter/10",
                HttpMethod.GET,
                new HttpEntity<>(result),
                String.class);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = (ow.writeValueAsString(studentService.filteringByAge(10))).toString();
        Assertions.assertThat(studentResponseEntity.getBody().toString()).isEqualTo(expectedJson);
        Assertions.assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testFilterMaxAndMinAgeStudent() throws Exception {
        Student student = new Student();
        student.setAge(200);
        student.setName("Dima");
        student.setId(8);
        String result = testRestTemplate.getForObject("http://localhost:" + port + "/student/age?min=100&max=201", String.class);
        ResponseEntity<String> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/age?min=100&max=201",
                HttpMethod.GET,
                new HttpEntity<>(result),
                String.class);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = List.of(ow.writeValueAsString(student)).toString();
        Assertions.assertThat(studentResponseEntity.getBody()).isEqualTo(expectedJson);
        Assertions.assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testDeleteStudent() throws Exception {
        long lastId = findLastStudentId();

        this.testRestTemplate.delete("http://localhost:" + port + "/student/" + findLastStudentId());

        assertNotEquals(findLastStudentId(), lastId);
    }
}
