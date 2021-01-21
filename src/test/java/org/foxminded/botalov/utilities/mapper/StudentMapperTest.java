package org.foxminded.botalov.utilities.mapper;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.student.Student;
import org.foxminded.botalov.model.student.StudentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(StudentMapperImpl.class)
public class StudentMapperTest {
    private final StudentMapper mapper;
    private final int studentId = 1;
    private final String firstName = "Ivan";
    private final String lastName = "Ivanov";
    private final int groupId = 1;
    private final String groupName = "group";

    @Autowired
    public StudentMapperTest(StudentMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    public void studentToStudentDto() {
        Student student = new Student(studentId, firstName, lastName, new Group(groupId, groupName));
        StudentDto expected = new StudentDto(studentId, firstName, lastName, groupId, groupName);
        StudentDto actual = mapper.studentToStudentDto(student);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void studentDtoToStudent() {
        StudentDto studentDto = new StudentDto(studentId, firstName, lastName, groupId, groupName);
        Student expected = new Student(studentId, firstName, lastName, new Group(groupId, groupName));
        Student actual = mapper.studentDtoToStudent(studentDto);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getGroup(), actual.getGroup());
    }
}
