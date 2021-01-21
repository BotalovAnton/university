package org.foxminded.botalov.service;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.lesson.Lesson;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.repository.LessonRepository;
import org.foxminded.botalov.repository.specification.forlesson.LessonFilter;
import org.foxminded.botalov.repository.specification.forlesson.LessonSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.LessonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class LessonServiceImplTest {
    private final LessonRepository repository = Mockito.mock(LessonRepository.class);
    private final LessonMapper mapper = Mockito.mock(LessonMapper.class);
    private final LessonSpecification lessonSpecification = Mockito.mock(LessonSpecification.class);
    private final LessonService service = new LessonServiceImpl(
            repository,
            mapper,
            lessonSpecification);

    private final int lessonId = 1;
    private final Instant start = Instant.parse("2020-11-11T09:00:00Z");
    private final Instant end = Instant.parse("2020-11-11T10:30:00Z");
    private final int courseId = 1;
    private final String courseName = "course";
    private final int groupId = 2;
    private final String groupName = "group";
    private final int teacherId = 3;
    private final String teacherFirstName = "Ivan";
    private final String teacherLastName = "Ivanov";
    private final int classroom = 221;

    @Test
    public void save() {
        LessonDto savingDto = new LessonDto(
                start,
                end,
                courseId,
                groupId,
                teacherId,
                classroom);

        Lesson saving = new Lesson(
                start,
                end,
                new Course(courseId),
                new Group(groupId),
                new Teacher(teacherId),
                classroom);

        Lesson saved = new Lesson(
                lessonId,
                start,
                end,
                new Course(courseId, courseName),
                new Group(groupId, groupName),
                new Teacher(teacherId, teacherFirstName, teacherLastName),
                classroom);

        LessonDto savedDto = new LessonDto(
                lessonId,
                start,
                end,
                courseId,
                courseName,
                groupId,
                groupName,
                teacherId,
                teacherFirstName,
                teacherLastName,
                classroom);

        Mockito.when(mapper.lessonDtoToLesson(savingDto)).thenReturn(saving);
        Mockito.when(mapper.lessonToLessonDto(saved)).thenReturn(savedDto);

        service.save(savingDto);

        Mockito.verify(mapper, Mockito.times(1)).lessonDtoToLesson(savingDto);
        Mockito.verify(repository, Mockito.times(1)).save(saving);
    }

    @Test
    public void findById() {
        Lesson found = new Lesson(
                lessonId,
                start,
                end,
                new Course(courseId, courseName),
                new Group(groupId, groupName),
                new Teacher(teacherId, teacherFirstName, teacherLastName),
                classroom);

        LessonDto expected = new LessonDto(
                lessonId,
                start,
                end,
                courseId,
                courseName,
                groupId,
                groupName,
                teacherId,
                teacherFirstName,
                teacherLastName,
                classroom
        );

        Mockito.when(repository.findById(lessonId)).thenReturn(Optional.ofNullable(found));
        Mockito.when(mapper.lessonToLessonDto(found)).thenReturn(expected);

        LessonDto actual = service.findById(lessonId);

        Mockito.verify(repository, Mockito.times(1)).findById(lessonId);
        Mockito.verify(mapper, Mockito.times(1)).lessonToLessonDto(found);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        int lesson1Id = 1;
        Instant start1 = Instant.parse("2020-11-23T09:00:00Z");
        Instant end1 = Instant.parse("2020-11-23T10:30:00Z");

        int lesson2Id = 2;
        Instant start2 = Instant.parse("2020-11-24T09:00:00Z");
        Instant end2 = Instant.parse("2020-11-24T10:30:00Z");



        Lesson found1 = new Lesson(
                lesson1Id,
                start1,
                end1,
                new Course(courseId, courseName),
                new Group(groupId, groupName),
                new Teacher(teacherId, teacherFirstName, teacherLastName),
                classroom
        );

        Lesson found2 = new Lesson(
                lesson2Id,
                start2,
                end2,
                new Course(courseId, courseName),
                new Group(groupId, groupName),
                new Teacher(teacherId, teacherFirstName, teacherLastName),
                classroom
        );

        List<Lesson> foundList = Arrays.asList(found1, found2);

        LessonDto expected1 = new LessonDto(
                lesson1Id,
                start1,
                end1,
                courseId,
                courseName,
                groupId,
                groupName,
                teacherId,
                teacherFirstName,
                teacherLastName,
                classroom
        );

        LessonDto expected2 = new LessonDto(
                lesson2Id,
                start2,
                end2,
                courseId,
                courseName,
                groupId,
                groupName,
                teacherId,
                teacherFirstName,
                teacherLastName,
                classroom
        );

        List<LessonDto> expectedList = Arrays.asList(expected1, expected2);

        Mockito.when(repository.findAll()).thenReturn(foundList);
        Mockito.when(mapper.lessonToLessonDto(foundList)).thenReturn(expectedList);

        List<LessonDto> actual = service.findAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verify(mapper, Mockito.times(1)).lessonToLessonDto(foundList);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    public void update() {
        int lessonId = 1;
        LessonDto lessonDto = new LessonDto(lessonId);
        lessonDto.setGroupId(groupId);
        lessonDto.setCourseId(courseId);
        lessonDto.setTeacherId(teacherId);

        Mockito.when(mapper.lessonDtoToLesson(lessonDto)).thenReturn(new Lesson(lessonId));

        service.update(lessonDto);

        Mockito.verify(repository, Mockito.times(1)).save(new Lesson(lessonId));
        Mockito.verify(mapper, Mockito.times(1)).lessonDtoToLesson(lessonDto);
    }

    @Test
    public void delete() {
        int lessonId = 1;

        service.delete(new LessonDto(lessonId));

        Mockito.verify(repository, Mockito.times(1)).delete(new Lesson(lessonId));
    }

    @Test
    public void getStudentLessonsShouldReturnSortedListLessons() {
        int studentId = 1;
        Instant start1 = Instant.parse("2020-11-11T09:00:00Z");
        Instant start2 = Instant.parse("2020-11-11T10:30:00Z");
        Instant start3 = Instant.parse("2020-11-11T12:00:00Z");

        Lesson lesson1 = new Lesson();
        lesson1.setStart(start1);
        lesson1.setCourse(new Course(courseId));
        lesson1.setGroup(new Group(groupId));
        lesson1.setTeacher(new Teacher(teacherId));


        Lesson lesson2 = new Lesson();
        lesson2.setStart(start2);
        lesson2.setCourse(new Course(courseId));
        lesson2.setGroup(new Group(groupId));
        lesson2.setTeacher(new Teacher(teacherId));

        Lesson lesson3 = new Lesson();
        lesson3.setStart(start3);
        lesson3.setCourse(new Course(courseId));
        lesson3.setGroup(new Group(groupId));
        lesson3.setTeacher(new Teacher(teacherId));

        LessonDto lessonDto1 = new LessonDto();
        lessonDto1.setStart(start1);
        LessonDto lessonDto2 = new LessonDto();
        lessonDto2.setStart(start2);
        LessonDto lessonDto3 = new LessonDto();
        lessonDto3.setStart(start3);

        Mockito.when(repository.getStudentLessons(studentId)).thenReturn(Arrays.asList(lesson3, lesson2, lesson1));
        Mockito.when(mapper.lessonToLessonDto(Arrays.asList(lesson1, lesson2, lesson3)))
                .thenReturn(Arrays.asList(lessonDto1, lessonDto2, lessonDto3));

        List<LessonDto> actual = service.getStudentLessons(studentId);

        Mockito.verify(repository, Mockito.times(1)).getStudentLessons(studentId);

        Assertions.assertEquals(Arrays.asList(lessonDto1, lessonDto2, lessonDto3), actual);
    }

    @Test
    public void getTeacherLessonsShouldReturnSortedListLessons() {
        Instant start1 = Instant.parse("2020-11-11T09:00:00Z");
        Instant start2 = Instant.parse("2020-11-11T10:30:00Z");
        Instant start3 = Instant.parse("2020-11-11T12:00:00Z");

        Lesson lesson1 = new Lesson();
        lesson1.setStart(start1);
        lesson1.setCourse(new Course(courseId));
        lesson1.setGroup(new Group(groupId));
        lesson1.setTeacher(new Teacher(teacherId));


        Lesson lesson2 = new Lesson();
        lesson2.setStart(start2);
        lesson2.setCourse(new Course(courseId));
        lesson2.setGroup(new Group(groupId));
        lesson2.setTeacher(new Teacher(teacherId));

        Lesson lesson3 = new Lesson();
        lesson3.setStart(start3);
        lesson3.setCourse(new Course(courseId));
        lesson3.setGroup(new Group(groupId));
        lesson3.setTeacher(new Teacher(teacherId));

        LessonDto lessonDto1 = new LessonDto();
        lessonDto1.setStart(start1);
        LessonDto lessonDto2 = new LessonDto();
        lessonDto2.setStart(start2);
        LessonDto lessonDto3 = new LessonDto();
        lessonDto3.setStart(start3);

        Mockito.when(repository.getLessonsByTeacherId(teacherId)).thenReturn(Arrays.asList(lesson3, lesson2, lesson1));
        Mockito.when(mapper.lessonToLessonDto(Arrays.asList(lesson1, lesson2, lesson3)))
                .thenReturn(Arrays.asList(lessonDto1, lessonDto2, lessonDto3));

        List<LessonDto> actual = service.getTeacherLessons(teacherId);

        Mockito.verify(repository, Mockito.times(1)).getLessonsByTeacherId(teacherId);

        Assertions.assertEquals(Arrays.asList(lessonDto1, lessonDto2, lessonDto3), actual);
    }

    @Test
    public  void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(lessonId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(lessonId));
        Assertions.assertEquals(String.format("lesson with id %s not found", lessonId), throwable.getMessage());
    }

    @Test
    public void getFilteredLessons() {
        Mockito.when(lessonSpecification.filterLessons(any(LessonFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredLessons(new LessonFilter());

        Mockito.verify(lessonSpecification, Mockito.times(1)). filterLessons(any(LessonFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).lessonToLessonDto(any(List.class));
    }
}
