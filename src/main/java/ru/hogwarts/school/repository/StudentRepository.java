package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findAllByAgeBetween(int minAge, int maxAge);

    Collection<Student> findAllByAge(int age);

    @Query(value = "SELECT count(id) FROM students", nativeQuery = true)
    int totalCountOfStudents();

    @Query(value = "SELECT avg(age) FROM students", nativeQuery = true)
    double averageAgeOfStudents();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT :count", nativeQuery = true)
    List<Student> lastStudents(@Param("count") int count);
}
