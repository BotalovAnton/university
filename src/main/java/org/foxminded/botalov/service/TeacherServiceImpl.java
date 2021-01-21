package org.foxminded.botalov.service;

import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.foxminded.botalov.repository.TeacherRepository;
import org.foxminded.botalov.repository.specification.forteacher.TeacherFilter;
import org.foxminded.botalov.repository.specification.forteacher.TeacherSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository repository;
    private final TeacherMapper mapper;
    private final TeacherSpecification teacherSpecification;

    @Autowired
    public TeacherServiceImpl(TeacherRepository repository,
                              TeacherMapper mapper,
                              TeacherSpecification teacherSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.teacherSpecification = teacherSpecification;
    }

    @Override
    public TeacherDto save(TeacherDto teacherDto) {
        Teacher teacher = mapper.teacherDtoToTeacher(teacherDto);

        teacher = repository.save(teacher);

        return mapper.teacherToTeacherDto(teacher);
    }

    @Override
    public TeacherDto findById(Integer id) {
        Teacher teacher;

        teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("teacher with id %s not found", id)));

        return mapper.teacherToTeacherDto(teacher);
    }

    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = repository.findAll();

        teachers.sort(Comparator.comparing(Teacher::getId));

        return mapper.teacherToTeacherDto(teachers);
    }

    @Override
    public void update(TeacherDto teacherDto) {

        repository.save(mapper.teacherDtoToTeacher(teacherDto));
    }

    @Override
    public void delete(TeacherDto teacherDto) {
        repository.delete(new Teacher(teacherDto.getId()));
    }

    @Override
    public List<TeacherDto> getFilteredTeachers(TeacherFilter filter) {
        Specification<Teacher> specification = teacherSpecification.filterTeachers(filter);

        return mapper.teacherToTeacherDto(repository.findAll(specification));
    }
}
