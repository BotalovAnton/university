package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.student.Student;
import org.foxminded.botalov.model.student.StudentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "groupId", source = "student.group.id")
    @Mapping(target = "groupName", source = "student.group.name")
    StudentDto studentToStudentDto(Student student);

    @InheritInverseConfiguration
    Student studentDtoToStudent(StudentDto studentDto);

    List<StudentDto> studentToStudentDto(List<Student> student);

    List<Student> studentDtoToStudent(List<StudentDto> studentDto);
}
