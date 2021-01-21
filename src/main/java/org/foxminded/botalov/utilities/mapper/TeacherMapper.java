package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "departmentId", source = "teacher.department.id")
    @Mapping(target = "departmentName", source = "teacher.department.name")
    TeacherDto teacherToTeacherDto(Teacher teacher);

    @InheritInverseConfiguration
    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    List<TeacherDto> teacherToTeacherDto(List<Teacher> teacher);

    List<Teacher> teacherDtoToTeacher(List<TeacherDto> teacherDto);
}
