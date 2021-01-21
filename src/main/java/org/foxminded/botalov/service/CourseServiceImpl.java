package org.foxminded.botalov.service;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.repository.CourseRepository;
import org.foxminded.botalov.repository.specification.forcourse.CourseFilter;
import org.foxminded.botalov.repository.specification.forcourse.CourseSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final CourseMapper mapper;
    private final CourseSpecification courseSpecification;

    @Autowired
    public CourseServiceImpl(CourseRepository repository,
                             CourseMapper mapper,
                             CourseSpecification courseSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.courseSpecification = courseSpecification;
    }

    public CourseDto save(CourseDto courseDto) {
        Course course = repository.save(mapper.courseDtoToCourse(courseDto));

        return mapper.courseToCourseDto(course);
    }

    @Override
    public CourseDto findById(Integer id) {
        Course course = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("course with id %s not found", id)));

        return mapper.courseToCourseDto(course);
    }

    @Override
    public List<CourseDto> findAll() {
        List<Course> courses = repository.findAll();

        courses.sort(Comparator.comparing(Course::getId));

        return mapper.courseToCourseDto(courses);
    }

    @Override
    public void update(CourseDto courseDto) {
        repository.save(mapper.courseDtoToCourse(courseDto));
    }

    @Override
    public void delete(CourseDto courseDto) {
        repository.delete(mapper.courseDtoToCourse(courseDto));
    }

    @Override
    public List<CourseDto> getFilteredCourses(CourseFilter filter) {
        Specification<Course> result = courseSpecification.filterCourses(filter);

        return mapper.courseToCourseDto(repository.findAll(result));
    }
}
