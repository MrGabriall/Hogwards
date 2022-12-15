package ru.hogwarts.school.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentRecord getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PostMapping()
    public StudentRecord createStudent(@RequestBody @Valid StudentRecord studentRecord) {
        return studentService.addStudent(studentRecord);
    }

    @PutMapping("/{id}")
    public StudentRecord editStudent(@PathVariable Long id, @RequestBody @Valid StudentRecord studentRecord) {
        return studentService.editStudent(id, studentRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/find-students")
    public Collection<StudentRecord> findStudents(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping(params = {"min", "max"})
    public Collection<StudentRecord> findByAgeBetween(@RequestParam("min") int minAge, @RequestParam("max") int maxAge){
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord findFacultyByStudent(@PathVariable long id){
        return studentService.findFacultyByStudent(id);
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents(){
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents(){
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lstStudents")
    public List<StudentRecord> lastStudents(@RequestParam @Min(1) @Max(5) int count){
        return studentService.lastStudents(count);
    }

    @GetMapping("/findStudentNamesWhichStartedWithA")
    public Stream<String> findStudentNamesWhichStartedWithA(){
        return studentService.findStudentNamesWhichStartedWithA();
    }

    @GetMapping("/findStudentAverageAge")
    public Double findStudentAverageAge(){
        return studentService.findStudentAverageAge();
    }
}
