package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    Map<Long, Faculty> facultyMap = new HashMap<>();
    long idCounter = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++idCounter);
        facultyMap.put(idCounter, faculty);
        return faculty;
    }

    public Faculty readFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }
    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);
    }

    public ArrayList<Faculty> filteringByAge(int color) {
        ArrayList<Faculty> faculriColor = new ArrayList<>();
        for (Faculty e :facultyMap.values()) {
            if(!e.getColor().equals(color)) {
                    continue;
                }
            faculriColor.add(e);
        };
        return faculriColor;
    }
}





