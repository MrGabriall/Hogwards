package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;
    @Value("{avatars.dir.path}")
    private String avatarsDir;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public StudentRecord addStudent(StudentRecord studentRecord) {
        LOGGER.debug("Method addFaculty was invoked");
        Student student = recordMapper.toEntity(studentRecord);
        Faculty faculty = Optional.ofNullable(studentRecord.getFaculty())
                .map(FacultyRecord::getId)
                .flatMap(facultyRepository::findById)
                .orElse(null);
        student.setFaculty(faculty);
        return recordMapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord findStudent(Long id) {
        LOGGER.debug("Method findStudent was invoked");
        return recordMapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)));
    }

    public StudentRecord editStudent(Long id, StudentRecord studentRecord) {
        LOGGER.debug("Method editStudent was invoked");
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public void deleteStudent(Long id) {
        LOGGER.debug("Method deleteStudent was invoked");
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
    }

    public Collection<StudentRecord> findByAge(int age) {
        LOGGER.debug("Method findByAge was invoked");
        return studentRepository.findByAge(age)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        LOGGER.debug("Method findByAgeBetween was invoked");
        return studentRepository.findAllByAgeBetween(minAge, maxAge)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord findFacultyByStudent(long id) {
        LOGGER.debug("Method findFacultyByStudent was invoked");
        return findStudent(id).getFaculty();
    }

    public int totalCountOfStudents() {
        LOGGER.debug("Method totalCountOfStudents was invoked");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        LOGGER.debug("Method averageAgeOfStudents was invoked");
        return studentRepository.averageAgeOfStudents();
    }

    public List<StudentRecord> lastStudents(int count) {
        LOGGER.debug("Method lastStudents was invoked");
        return studentRepository.lastStudents(count).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Stream<String> findStudentNamesWhichStartedWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted();
    }


    public double findStudentAverageAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    public void getAllStudentsInThreeThreads() {
        LOGGER.debug("Method getAllStudentsWithThreads was invoked");
        List<Student> students = studentRepository.findAll();
        printStudent(students.get(0));
        printStudent(students.get(1));

        new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        }).start();

        new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        }).start();
    }

    public void getAllStudentsInThreeSynchronizedThreads() {
        LOGGER.debug("Method getAllStudentsWithSynchronizedThreads was invoked");
        List<Student> students = studentRepository.findAll();
        printStudentSynchronized(students.get(0), students.get(1));

        new Thread(() -> {
            printStudentSynchronized(students.get(2), students.get(3));
        }).start();

        new Thread(() -> {
            printStudentSynchronized(students.get(4), students.get(5));
        }).start();
    }

    public void printStudent(Student student) {
        System.out.println(student.getName());
    }

    public synchronized void printStudentSynchronized(Student student, Student student2) {
        System.out.println(student.getName());
        System.out.println(student2.getName());
    }
}
