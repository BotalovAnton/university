package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.course.CourseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "departmentId", source = "course.department.id")
    @Mapping(target = "departmentName", source = "course.department.name")
    CourseDto courseToCourseDto(Course course);

    @InheritInverseConfiguration
    Course courseDtoToCourse(CourseDto courseDto);

    List<CourseDto> courseToCourseDto(List<Course> course);

    List<Course> courseDtoToCourse(List<CourseDto> courseDto);
}
