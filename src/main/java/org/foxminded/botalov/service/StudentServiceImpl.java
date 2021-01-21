package org.foxminded.botalov.service;

import org.foxminded.botalov.model.student.Student;
import org.foxminded.botalov.model.student.StudentDto;
import org.foxminded.botalov.repository.StudentRepository;
import org.foxminded.botalov.repository.specification.forstudent.StudentFilter;
import org.foxminded.botalov.repository.specification.forstudent.StudentSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final StudentSpecification studentSpecification;

    @Autowired
    public StudentServiceImpl(StudentRepository repository,
                              StudentMapper mapper,
                              StudentSpecification studentSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.studentSpecification = studentSpecification;
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        Student student = mapper.studentDtoToStudent(studentDto);

        student = repository.save(student);

        return mapper.studentToStudentDto(student);
    }

    @Override
    public StudentDto findById(Integer id) {
        Student student;

        student = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("student with id %s not found", id)));

        return mapper.studentToStudentDto(student);
    }

    @Override
    public List<StudentDto> findAll() {
        List<Student> students = repository.findAll();

        students.sort(Comparator.comparing(Student::getId));

        return mapper.studentToStudentDto(students);
    }

    @Override
    public void update(StudentDto studentDto) {

        repository.save(mapper.studentDtoToStudent(studentDto));
    }

    @Override
    public void delete(StudentDto studentDto) {
        repository.delete(new Student(studentDto.getId()));
    }

    @Override
    public List<StudentDto> getFilteredStudents(StudentFilter filter) {
        Specification<Student> specification = studentSpecification.filterStudents(filter);

        return mapper.studentToStudentDto(repository.findAll(specification));
    }
}
