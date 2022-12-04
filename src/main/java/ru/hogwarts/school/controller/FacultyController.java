package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("Faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public FacultyRecord getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping()
    public FacultyRecord createFaculty(@RequestBody @Valid FacultyRecord facultyRecord) {
        return facultyService.addFaculty(facultyRecord);
    }

    @PutMapping("/{id}")
    public FacultyRecord editFaculty(@PathVariable Long id, @RequestBody @Valid FacultyRecord facultyRecord) {
        return facultyService.editFaculty(id, facultyRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping(params = "!colorOrName")
    public Collection<FacultyRecord> findFaculties(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping(params = "colorOrName")
    public Collection<FacultyRecord> findByNameOrColor(@RequestParam String colorOrName) {
        return facultyService.findByNameOrColor(colorOrName);
    }

    @GetMapping("/{id}/students")
    public Collection<StudentRecord> findStudentByFaculty(@PathVariable long id){
        return facultyService.findStudentByFaculty(id);
    }

}
