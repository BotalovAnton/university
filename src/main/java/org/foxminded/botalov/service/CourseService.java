package org.foxminded.botalov.service;

import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.repository.specification.forcourse.CourseFilter;

import java.util.List;

public interface CourseService {
    CourseDto save(CourseDto courseDto);

    CourseDto findById(Integer id);

    List<CourseDto> findAll();

    void update(CourseDto course);

    void delete(CourseDto course);

    List<CourseDto> getFilteredCourses(CourseFilter filter);
}
