package org.foxminded.botalov.service;

import org.foxminded.botalov.model.teacher.TeacherDto;
import org.foxminded.botalov.repository.specification.forteacher.TeacherFilter;

import java.util.List;

public interface TeacherService {

    TeacherDto save(TeacherDto teacherDto);

    TeacherDto findById(Integer id);

    List<TeacherDto> findAll();

    void update(TeacherDto teacherDto);

    void delete(TeacherDto teacherDto);

    List<TeacherDto> getFilteredTeachers(TeacherFilter filter);
}
