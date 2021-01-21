package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.student.StudentDto;
import org.foxminded.botalov.repository.specification.forstudent.StudentFilter;
import org.foxminded.botalov.service.StudentService;
import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getAll() {
        return studentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody StudentDto studentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/student/%s", studentService.save(studentDto).getId())));

        return headers;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        studentService.delete(new StudentDto(id));
    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable("id") int id) {
        return studentService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(value = CheckUpdatedEntity.class) @RequestBody StudentDto studentDto) {
        studentService.update(studentDto);
    }

    @GetMapping("/filter")
    public List<StudentDto> findByFilter(StudentFilter studentFilter) {
        return studentService.getFilteredStudents(studentFilter);
    }
}
