package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.lesson.Lesson;
import org.foxminded.botalov.model.lesson.LessonDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "courseId", source = "lesson.course.id")
    @Mapping(target = "courseName", source = "lesson.course.name")
    @Mapping(target = "groupId", source = "lesson.group.id")
    @Mapping(target = "groupName", source = "lesson.group.name")
    @Mapping(target = "teacherId", source = "lesson.teacher.id")
    @Mapping(target = "teacherFirstName", source = "lesson.teacher.firstName")
    @Mapping(target = "teacherLastName", source = "lesson.teacher.lastName")
    LessonDto lessonToLessonDto(Lesson lesson);

    @InheritInverseConfiguration
    Lesson lessonDtoToLesson(LessonDto lessonDto);

    List<LessonDto> lessonToLessonDto(List<Lesson> lesson);

    List<Lesson> lessonDtoToLesson(List<LessonDto> lessonDto);
}
