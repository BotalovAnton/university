package org.foxminded.botalov.service;

import org.foxminded.botalov.model.student.StudentDto;
import org.foxminded.botalov.repository.specification.forstudent.StudentFilter;

import java.util.List;

public interface StudentService {
    StudentDto save(StudentDto studentDto);

    StudentDto findById(Integer id);

    List<StudentDto> findAll();

    void update(StudentDto studentDto);

    void delete(StudentDto studentDto);

    List<StudentDto> getFilteredStudents(StudentFilter filter);
}
