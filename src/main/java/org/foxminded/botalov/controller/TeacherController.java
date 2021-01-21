package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.teacher.TeacherDto;
import org.foxminded.botalov.repository.specification.forteacher.TeacherFilter;
import org.foxminded.botalov.service.TeacherService;
import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDto> getAll() {
        return teacherService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody TeacherDto teacherDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/teacher/%s", teacherService.save(teacherDto).getId())));

        return headers;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        teacherService.delete(new TeacherDto(id));
    }

    @GetMapping("/{id}")
    public TeacherDto findById(@PathVariable int id) {
        return teacherService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(value = CheckUpdatedEntity.class) @RequestBody TeacherDto teacherDto) {
        teacherService.update(teacherDto);
    }

    @GetMapping("/filter")
    public List<TeacherDto> getFilteredTeachers(TeacherFilter teacherFilter) {
        return teacherService.getFilteredTeachers(teacherFilter);
    }
}
