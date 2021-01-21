package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(TeacherMapperImpl.class)
public class TeacherMapperTest {
    private final TeacherMapper mapper;
    private final int teacherId = 1;
    private final String firstName = "Ivan";
    private final String lastName = "Ivanov";
    private final int departmentId = 1;
    private final String departmentName = "department";

    @Autowired
    public TeacherMapperTest(TeacherMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void teacherToTeacherDto() {
        int teacherId = 1;
        String firstName = "Ivan";
        String lastName = "Ivanov";
        int departmentId = 1;
        String departmentName = "department";

        Teacher teacher = new Teacher(teacherId, firstName, lastName, new Department(departmentId, departmentName));
        TeacherDto expected = new TeacherDto(teacherId, firstName, lastName, departmentId, departmentName);
        TeacherDto actual = mapper.teacherToTeacherDto(teacher);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void studentDTOToStudent() {
        TeacherDto teacherDto = new TeacherDto(teacherId, firstName, lastName, departmentId, departmentName);
        Teacher expected = new Teacher(teacherId, firstName, lastName, new Department(departmentId, departmentName));
        Teacher actual = mapper.teacherDtoToTeacher(teacherDto);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getDepartment(), actual.getDepartment());
    }
}
