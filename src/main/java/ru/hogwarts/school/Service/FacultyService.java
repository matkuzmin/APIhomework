package ru.hogwarts.school.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()) != null) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filteringByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(col -> col.getColor() == color)
                .collect(Collectors.toList());


    }
}





