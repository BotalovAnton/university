package org.foxminded.botalov.service;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.repository.CourseRepository;
import org.foxminded.botalov.repository.specification.forcourse.CourseFilter;
import org.foxminded.botalov.repository.specification.forcourse.CourseSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.CourseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class CourseServiceImplTest {
    private final CourseMapper mapper = Mockito.mock(CourseMapper.class);
    private final CourseRepository repository = Mockito.mock(CourseRepository.class);
    private final CourseSpecification courseSpecification = Mockito.mock(CourseSpecification.class);
    private final CourseService service = new CourseServiceImpl(repository, mapper, courseSpecification);

    private final int courseId = 1;
    private final String courseName = "course";
    private final int departmentId = 1;
    private final String departmentName = "department";

    @Test
    public void save() {
        Mockito.when(mapper.courseDtoToCourse(new CourseDto(courseName, departmentId, departmentName)))
                .thenReturn(new Course(courseName, new Department(departmentId, departmentName)));

        Mockito.when(mapper.courseToCourseDto(new Course(courseId, courseName, new Department(departmentId, departmentName))))
                .thenReturn(new CourseDto(courseId, courseName, departmentId, departmentName));

        service.save(new CourseDto(courseName, departmentId, departmentName));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Course(courseName, new Department(departmentId, departmentName)));

    }

    @Test
    public void findById() {
        Mockito.when(mapper.courseToCourseDto(new Course(courseId, courseName, new Department(departmentId, departmentName))))
                .thenReturn(new CourseDto(courseId, courseName, departmentId, departmentName));

        Mockito.when(repository.findById(courseId))
                .thenReturn(Optional.ofNullable(new Course(courseId, courseName, new Department(departmentId))));

        CourseDto actual = service.findById(courseId);

        Mockito.verify(repository, Mockito.times(1)).findById(courseId);
        Assertions.assertEquals(new CourseDto(courseId, courseName, departmentId, departmentName), actual);
    }

    @Test
    public void findAll() {
        int course1Id = 1;
        String course1Name = "course1";
        int course2Id = 2;
        String course2Name = "course2";

        List<CourseDto> expected = Arrays.asList(
                new CourseDto(course1Id, course1Name, departmentId, departmentName),
                new CourseDto(course2Id, course2Name, departmentId, departmentName));

        List<Course> returned = Arrays.asList(
                new Course(course1Id, course1Name, new Department(departmentId)),
                new Course(course2Id, course2Name, new Department(departmentId)));

        Mockito.when(repository.findAll())
                .thenReturn(returned);

        Mockito.when(mapper.courseToCourseDto(Arrays.asList(
                new Course(course1Id, course1Name, new Department(departmentId, departmentName)),
                new Course(course2Id, course2Name, new Department(departmentId, departmentName)))))
                .thenReturn(Arrays.asList(
                        new CourseDto(course1Id, course1Name, departmentId, departmentName),
                        new CourseDto(course2Id, course2Name, departmentId, departmentName)));

        List<CourseDto> actual = service.findAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Mockito.when(mapper.courseDtoToCourse(new CourseDto(courseId, courseName, departmentId)))
                .thenReturn(new Course(courseId, courseName, new Department(departmentId)));

        service.update(new CourseDto(courseId, courseName, departmentId));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Course(courseId, courseName, new Department(departmentId)));
    }

    @Test
    public void delete() {
        Mockito.when(mapper.courseDtoToCourse(new CourseDto(courseId)))
                .thenReturn(new Course(courseId));

        service.delete(new CourseDto(courseId));

        Mockito.verify(repository, Mockito.times(1))
                .delete(new Course(courseId));
    }

    @Test
    public void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(courseId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(courseId));
        Assertions.assertEquals(String.format("course with id %s not found", courseId), throwable.getMessage());
    }

    @Test
    public void getFilteredCourses() {
        Mockito.when(courseSpecification.filterCourses(any(CourseFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredCourses(new CourseFilter());

        Mockito.verify(courseSpecification, Mockito.times(1)).filterCourses(any(CourseFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).courseToCourseDto(any(List.class));
    }
}
