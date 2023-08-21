package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {

        logger.info("Start createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        logger.info("Start readFaculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Start updateFaculty");
        if (facultyRepository.findById(faculty.getId()) != null) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        logger.info("Start deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filteringByColor(String color) {
        logger.info("Start filteringByColor");
        return facultyRepository.findAll().stream()
                .filter(col -> col.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findFaculty(String name, String color) {
        logger.info("Start findFaculty");
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty findFacultyStudent(Student student) {
        logger.info("Start findFacultyStudent");
        return facultyRepository.findFacultiesByStudents(student);
    }
}





