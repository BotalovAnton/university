package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.course.CourseDto;
import org.foxminded.botalov.repository.specification.forcourse.CourseFilter;
import org.foxminded.botalov.service.CourseService;
import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getAll() {
        return courseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody CourseDto courseDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/course/%s", courseService.save(courseDto).getId())));

        return headers;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable int id) {
        courseService.delete(new CourseDto(id));
    }

    @GetMapping("/{id}")
    public CourseDto findById(@PathVariable("id") int id) {
        return courseService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(value = CheckUpdatedEntity.class) @RequestBody CourseDto courseDto) {
       courseService.update(courseDto);
    }

    @GetMapping("/filter")
    public List<CourseDto> findByFilter(CourseFilter courseFilter) {
        return courseService.getFilteredCourses(courseFilter);
    }
}
