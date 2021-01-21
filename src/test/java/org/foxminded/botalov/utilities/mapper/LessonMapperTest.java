package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.lesson.Lesson;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.foxminded.botalov.model.teacher.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Instant;

@SpringJUnitConfig(LessonMapperImpl.class)
public class LessonMapperTest {
    private final LessonMapper mapper;
    private final int lessonId = 1;
    private final Instant start = Instant.parse("2020-11-11T09:00:00Z");
    private final Instant end = Instant.parse("2020-11-11T10:30:00Z");
    private final int courseId = 1;
    private final String courseName = "course";
    private final int groupId = 1;
    private final String groupName = "group";
    private final int teacherId = 1;
    private final String teacherFirstName = "Ivan";
    private final String teacherLastName = "Ivanov";
    private final int classroom = 101;

    @Autowired
    public LessonMapperTest(LessonMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void lessonToLessonDTO() {


        Lesson lesson = new Lesson(
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
                classroom);

        LessonDto actual = mapper.lessonToLessonDto(lesson);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void lessonDtoToLesson() {
        LessonDto lessonDto = new LessonDto(
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

        Lesson expected = new Lesson(
                lessonId,
                start,
                end,
                new Course(courseId, courseName),
                new Group(groupId, groupName),
                new Teacher(teacherId, teacherFirstName, teacherLastName),
                classroom);

        Lesson actual = mapper.lessonDtoToLesson(lessonDto);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getGroup(), actual.getGroup());
        Assertions.assertEquals(expected.getTeacher(), actual.getTeacher());
    }
}
