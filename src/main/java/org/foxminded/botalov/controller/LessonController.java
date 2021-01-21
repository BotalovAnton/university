package org.foxminded.botalov.controller;

import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.repository.specification.forlesson.LessonFilter;
import org.foxminded.botalov.service.LessonService;
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
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<LessonDto> getAll() {
        return lessonService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpHeaders add(@Validated(value = CheckAddedEntity.class) @RequestBody LessonDto lessonDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/lesson/%s", lessonService.save(lessonDto).getId())));

        return headers;
    }

    @GetMapping("/{id}")
    public LessonDto findById(@PathVariable int id) {
        return lessonService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(value = CheckUpdatedEntity.class) @RequestBody LessonDto lessonDto) {
        lessonService.update(lessonDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        lessonService.delete(new LessonDto(id));
    }

    @GetMapping("/student")
    public List<LessonDto> getStudentLesson(@RequestParam("id") int studentId) {
        return lessonService.getStudentLessons(studentId);
    }

    @GetMapping("/teacher")
    public List<LessonDto> getTeacherLesson(@RequestParam("id") int teacherId) {
        return  lessonService.getTeacherLessons(teacherId);
    }

    @GetMapping("/filter")
    public List<LessonDto> getFilteredLessons(LessonFilter lessonFilter) {
        return lessonService.getFilteredLessons(lessonFilter);
    }
}
