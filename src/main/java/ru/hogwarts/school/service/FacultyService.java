package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord addFaculty(FacultyRecord facultyRecord) {
        LOGGER.debug("Method addFaculty was invoked");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord findFaculty(Long id) {
        LOGGER.debug("Method findFaculty was invoked");
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id)));
    }

    public FacultyRecord editFaculty(Long id, FacultyRecord facultyRecord) {
        LOGGER.debug("Method editFaculty was invoked");
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public void deleteFaculty(Long id) {
        LOGGER.debug("Method deleteFaculty was invoked");
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        LOGGER.debug("Method findByColor was invoked");
        return facultyRepository.findByColor(color)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByNameOrColor(String colorOrName) {
        LOGGER.debug("Method findByNameOrColor was invoked");
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName)
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findStudentByFaculty(long id) {
        LOGGER.debug("Method findStudentByFaculty was invoked");
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id))
                .getStudents()
                .stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
