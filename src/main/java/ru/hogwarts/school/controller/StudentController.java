package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/student")
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
}
