package org.foxminded.botalov.service;

import org.foxminded.botalov.model.lesson.Lesson;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.repository.LessonRepository;
import org.foxminded.botalov.repository.specification.forlesson.LessonFilter;
import org.foxminded.botalov.repository.specification.forlesson.LessonSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements LessonService {
    private final LessonRepository repository;
    private final LessonMapper mapper;
    private final LessonSpecification lessonSpecification;
    private final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Autowired
    public LessonServiceImpl(LessonRepository repository,
                             LessonMapper mapper,
                             LessonSpecification lessonSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.lessonSpecification = lessonSpecification;
    }

    @Override
    public LessonDto save(LessonDto lessonDto) {
        Lesson lesson = mapper.lessonDtoToLesson(lessonDto);

        lesson = repository.save(lesson);

        return mapper.lessonToLessonDto(lesson);
    }

    @Override
    public LessonDto findById(Integer id) {
        Lesson lesson = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("lesson with id %s not found", id)));

        return mapper.lessonToLessonDto(lesson);
    }

    @Override
    public List<LessonDto> findAll() {
        List<Lesson> lessons = repository.findAll();

        lessons.sort(Comparator.comparing(Lesson::getStart));

        return mapper.lessonToLessonDto(lessons);
    }

    @Override
    public void update(LessonDto lessonDto) {

        repository.save(mapper.lessonDtoToLesson(lessonDto));
    }

    @Override
    public void delete(LessonDto lessonDto) {
        repository.delete(new Lesson(lessonDto.getId()));
    }

    @Override
    public List<LessonDto> getStudentLessons(Integer studentId) {
        logger.debug("call method getStudentLessons with parameter studentId = {}", studentId);

        List<Lesson> lessons = repository.getStudentLessons(studentId);

        logger.debug("start sorting lessons for student with id {}", studentId);

        lessons.sort(Comparator.comparing(Lesson::getStart));

        return mapper.lessonToLessonDto(lessons);
    }

    @Override
    public List<LessonDto> getTeacherLessons(Integer teacherId) {
        logger.debug("call method getTeacherLessons with parameter teacherId = {}", teacherId);

        List<Lesson> lessons = repository.getLessonsByTeacherId(teacherId);

        logger.debug("start sorting lessons for teacher with id {}", teacherId);

        lessons.sort(Comparator.comparing(Lesson::getStart));

        return mapper.lessonToLessonDto(lessons);
    }

    @Override
    public List<LessonDto> getFilteredLessons(LessonFilter filter) {
        Specification<Lesson> specification = lessonSpecification.filterLessons(filter);

        return mapper.lessonToLessonDto(repository.findAll(specification));
    }
}
