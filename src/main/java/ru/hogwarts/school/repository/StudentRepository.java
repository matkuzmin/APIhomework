package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.Model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
