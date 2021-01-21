package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.model.department.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

@SpringJUnitConfig(CourseMapperImpl.class)
public class CourseMapperTest {
    private final CourseMapper mapper;
    private final int courseId = 1;
    private final String courseName = "course";
    private final int departmentId = 1;
    private final String departmentName = "department";

    @Autowired
    public CourseMapperTest(CourseMapper courseMapper) {
        this.mapper = courseMapper;
    }


    @Test
    public void courseToCourseDtoShouldReturnCorrectlyCourseDto() {
        Course course = new Course(courseId, courseName, new Department(departmentId, departmentName));
        CourseDto expected = new CourseDto(courseId, courseName, departmentId, departmentName);
        CourseDto actual = mapper.courseToCourseDto(course);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void courseDtoToCourseShouldReturnCorrectlyCourse() {
        CourseDto courseDto = new CourseDto(courseId, courseName, departmentId, departmentName);
        Course expected = new Course(courseId, courseName, new Department(departmentId, departmentName));
        Course actual = mapper.courseDtoToCourse(courseDto);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getDepartment(), actual.getDepartment());
    }

    @Test
    public void courseToCourseDtoShouldReturnCorrectlyCourseDtoList() {
        int course1Id = 1;
        String course1Name = "course1";
        int course2Id = 2;
        String course2Name = "course2";
        int departmentId = 1;
        String departmentName = "department";

        List<CourseDto> expected = Arrays.asList(
                new CourseDto(course1Id, course1Name, departmentId, departmentName),
                new CourseDto(course2Id, course2Name, departmentId, departmentName));

        List<Course> courses = Arrays.asList(
                new Course(course1Id, course1Name, new Department(departmentId, departmentName)),
                new Course(course2Id, course2Name, new Department(departmentId, departmentName)));

        List<CourseDto> actual = mapper.courseToCourseDto(courses);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void courseDtoToCourseShouldReturnCorrectlyCourseList() {
        int course1Id = 1;
        String course1Name = "course1";
        int course2Id = 2;
        String course2Name = "course2";
        int departmentId = 1;
        String departmentName = "department";

        List<CourseDto> courseDtoList = Arrays.asList(
                new CourseDto(course1Id, course1Name, departmentId, departmentName),
                new CourseDto(course2Id, course2Name, departmentId, departmentName));

        List<Course> expected = Arrays.asList(
                new Course(course1Id, course1Name, new Department(departmentId, departmentName)),
                new Course(course2Id, course2Name, new Department(departmentId, departmentName)));

        List<Course> actual = mapper.courseDtoToCourse(courseDtoList);

        Assertions.assertEquals(expected, actual);
    }
}
