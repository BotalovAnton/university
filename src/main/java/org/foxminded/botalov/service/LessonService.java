package org.foxminded.botalov.service;

import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.repository.specification.forlesson.LessonFilter;

import java.util.List;

public interface LessonService {
    LessonDto save(LessonDto lessonDto);

    LessonDto findById(Integer id);

    List<LessonDto> findAll();

    void update(LessonDto lessonDto);

    void delete(LessonDto lessonDto);

    List<LessonDto> getStudentLessons(Integer studentId);

    List<LessonDto> getTeacherLessons(Integer teacherId);

    List<LessonDto> getFilteredLessons(LessonFilter filter);
}
