package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeBetween(int min, int max);

    Collection<Student> findStudentByFaculty(Faculty faculty);
    Collection<Student> findAllByFacultyId(Long id);
    List<Student> findAll();
    @Query(value = "select count(id) from student",nativeQuery = true)
    Integer getCountStudent();
    @Query(value = "select avg(age) from student",nativeQuery = true)
    Integer AvgAgeStudent();
    @Query(value = "select * from student order by id desc limit 5",nativeQuery = true)
    List<Student> getLastFiveStudent();


}
