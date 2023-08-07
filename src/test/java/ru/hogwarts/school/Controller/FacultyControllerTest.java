

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
        import ru.hogwarts.school.Model.Faculty;
        import ru.hogwarts.school.Model.Student;
        import ru.hogwarts.school.Service.FacultyService;
        import ru.hogwarts.school.repository.FacultyRepository;
        import ru.hogwarts.school.repository.StudentRepository;

        import java.util.Comparator;
        import java.util.List;

        import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyService facultyService;

    private long findLastFacultyId() {
        List<Faculty> faculties = facultyRepository.findAll();
        Faculty lastFaculty = faculties.stream()
                .max(Comparator.comparing(Faculty::getId))
                .orElse(null);
        if (lastFaculty == null) {
            throw new NullPointerException("таблица со студентами пуста");
        }
        return lastFaculty.getId();
    }
    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testReadFaculty() throws Exception {
        Faculty actual = facultyRepository.findById(1L).get() ;
        Faculty result = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + 1, Faculty.class);
        Assertions.assertThat(result.getName()).isEqualTo(actual.getName());
        Assertions.assertThat(result.getColor()).isEqualTo(actual.getColor());
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Какой-то");
        faculty.setColor("RGB");
        Faculty result = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Assertions.assertThat(result.getName()).isEqualTo("Какой-то");
        Assertions.assertThat(result.getColor()).isEqualTo("RGB");
    }

    @Test
    void testPutFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor("Red");
        faculty.setName("Красный");
        Faculty result = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        result.setName("Harry");
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(result),
                Faculty.class);

        Assertions.assertThat(facultyResponseEntity.getBody().getName()).isEqualTo("Harry");
        Assertions.assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void testSortedFaculty() throws Exception {

        String result = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/sorted/red", String.class);
        ResponseEntity<String> facultyResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/sorted/red",
                HttpMethod.GET,
                new HttpEntity<>(result),
                String.class);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = (ow.writeValueAsString(facultyService.filteringByColor("red"))).toString();
        Assertions.assertThat(facultyResponseEntity.getBody()).isEqualTo(expectedJson);
        Assertions.assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }@Test
    void testFilterColorOrNameFaculty() throws Exception {
        String result = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/find?color=red", String.class);
        ResponseEntity<String> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/find?color=red",
                HttpMethod.GET,
                new HttpEntity<>(result),
                String.class);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = (ow.writeValueAsString(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(null,"red"))).toString();
        Assertions.assertThat(studentResponseEntity.getBody()).isEqualTo(expectedJson);
        Assertions.assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }@Test
    void testFindFacultyByStudent() throws Exception {
        Student student = new Student();
        student.setAge(10);
        student.setId(1);
        student.setName("Галя");
        ResponseEntity<String> studentResponseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/findByStudent",
                HttpMethod.POST,
                new HttpEntity<>(student),
                String.class);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = (ow.writeValueAsString(facultyRepository.findFacultiesByStudents(student))).toString();
        Assertions.assertThat(studentResponseEntity.getBody()).isEqualTo(expectedJson);
        Assertions.assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}


