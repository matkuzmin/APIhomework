

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
}