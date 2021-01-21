package org.foxminded.botalov.service;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.model.teacher.TeacherDto;
import org.foxminded.botalov.repository.TeacherRepository;
import org.foxminded.botalov.repository.specification.forteacher.TeacherFilter;
import org.foxminded.botalov.repository.specification.forteacher.TeacherSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.TeacherMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class TeacherServiceImplTest {
    private final TeacherRepository repository = Mockito.mock(TeacherRepository.class);
    private final TeacherMapper mapper = Mockito.mock(TeacherMapper.class);
    private final TeacherSpecification teacherSpecification = Mockito.mock(TeacherSpecification.class);
    private final TeacherService service = new TeacherServiceImpl(repository, mapper, teacherSpecification);

    private final int teacherId = 1;
    private final String teacherFirstName = "Ivan";
    private final String teacherLastName = "Ivanov";
    private final int departmentId = 2;
    private final String departmentName = "department";

    @Test
    public void save() {
        Teacher saving = new Teacher(teacherFirstName, teacherLastName, new Department(departmentId));
        TeacherDto savingDto = new TeacherDto(teacherFirstName, teacherLastName, departmentId);
        Teacher saved = new Teacher(teacherId, teacherFirstName, teacherLastName, new Department(departmentId));
        TeacherDto savedDto = new TeacherDto(teacherId, teacherFirstName, teacherLastName, departmentId, departmentName);
        TeacherDto expected = new TeacherDto(teacherId, teacherFirstName, teacherLastName, departmentId, departmentName);

        Mockito.when(mapper.teacherDtoToTeacher(savingDto)).thenReturn(saving);
        Mockito.when(mapper.teacherToTeacherDto(saved)).thenReturn(savedDto);

        service.save(savingDto);

        Mockito.verify(repository, Mockito.times(1)).save(saving);
    }

    @Test
    public void findById() {
        TeacherDto expected = new TeacherDto(teacherId, teacherFirstName, teacherLastName, departmentId, departmentName);
        TeacherDto filledDto = new TeacherDto(teacherId, teacherFirstName, teacherLastName, departmentId, departmentName);
        Teacher found = new Teacher(teacherId, teacherFirstName, teacherLastName, new Department(departmentId));

        Mockito.when(repository.findById(teacherId)).thenReturn(Optional.ofNullable(found));
        Mockito.when(mapper.teacherToTeacherDto(found)).thenReturn(filledDto);

        TeacherDto actual = service.findById(teacherId);

        Mockito.verify(repository, Mockito.times(1)).findById(teacherId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        int teacher1Id = 1;
        String teacher1FirstName = "Ivan";
        String teacher1LastName = "Ivanov";
        int teacher2Id = 3;
        String teacher2FirstName = "Petr";
        String teacher2LastName = "Petrov";
        int departmentId = 2;
        String departmentName = "department";

        List<TeacherDto> expected = Arrays.asList(
                new TeacherDto(teacher1Id, teacher1FirstName, teacher1LastName, departmentId, departmentName),
                new TeacherDto(teacher2Id, teacher2FirstName, teacher2LastName, departmentId, departmentName)
        );

        List<TeacherDto> filledDto = Arrays.asList(
                new TeacherDto(teacher1Id, teacher1FirstName, teacher1LastName, departmentId, departmentName),
                new TeacherDto(teacher2Id, teacher2FirstName, teacher2LastName, departmentId, departmentName)
        );

        List<Teacher> found = Arrays.asList(
                new Teacher(teacher1Id, teacher1FirstName, teacher1LastName, new Department(departmentId)),
                new Teacher(teacher2Id, teacher2FirstName, teacher2LastName, new Department(departmentId))
        );

        Mockito.when(repository.findAll()).thenReturn(found);
        Mockito.when(mapper.teacherToTeacherDto(found)).thenReturn(filledDto);

        List<TeacherDto> actual = service.findAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void update() {
        TeacherDto updatingDto = new TeacherDto(teacherId, teacherFirstName, teacherLastName, departmentId, departmentName);
        Teacher updating = new Teacher(teacherId, teacherFirstName, teacherLastName, new Department(departmentId, departmentName));

        Mockito.when(mapper.teacherDtoToTeacher(updatingDto)).thenReturn(updating);

        service.update(updatingDto);

        Mockito.verify(repository, Mockito.times(1)).save(updating);
    }

    @Test
    public void delete() {
        int teacherId = 1;

        service.delete(new TeacherDto(teacherId));

        Mockito.verify(repository, Mockito.times(1)).delete(new Teacher(teacherId));
    }

    @Test
    public void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(teacherId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(teacherId));
        Assertions.assertEquals(String.format("teacher with id %s not found", teacherId), throwable.getMessage());
    }

    @Test
    public void getFilteredTeachers() {
        Mockito.when(teacherSpecification.filterTeachers(any(TeacherFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredTeachers(new TeacherFilter());

        Mockito.verify(teacherSpecification, Mockito.times(1)).filterTeachers(any(TeacherFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).teacherToTeacherDto(any(List.class));
    }
}
