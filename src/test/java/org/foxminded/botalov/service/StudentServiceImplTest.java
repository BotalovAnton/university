package org.foxminded.botalov.service;

import org.foxminded.botalov.model.group.Group;
import org.foxminded.botalov.model.student.Student;
import org.foxminded.botalov.model.student.StudentDto;
import org.foxminded.botalov.repository.StudentRepository;
import org.foxminded.botalov.repository.specification.forstudent.StudentFilter;
import org.foxminded.botalov.repository.specification.forstudent.StudentSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class StudentServiceImplTest {
    private final StudentRepository repository = Mockito.mock(StudentRepository.class);
    private final StudentMapper mapper = Mockito.mock(StudentMapper.class);
    private final StudentSpecification studentSpecification = Mockito.mock(StudentSpecification.class);
    private final StudentService service = new StudentServiceImpl(repository, mapper, studentSpecification);

    private final int studentId = 1;
    private final String studentFirstName = "Ivan";
    private final String studentLastName = "Ivanov";
    private final int groupId = 2;
    private final String groupName = "group";

    @Test
    public void save() {
        Student saving = new Student(studentFirstName, studentLastName, new Group(groupId));
        StudentDto savingDto = new StudentDto(studentFirstName, studentLastName, groupId);
        Student saved = new Student(studentId, studentFirstName, studentLastName, new Group(groupId));
        StudentDto savedDTO = new StudentDto(studentId, studentFirstName, studentLastName, groupId, groupName);
        StudentDto expected = new StudentDto(studentId, studentFirstName, studentLastName, groupId, groupName);

        Mockito.when(mapper.studentDtoToStudent(savingDto)).thenReturn(saving);
        Mockito.when(mapper.studentToStudentDto(saved)).thenReturn(savedDTO);

        service.save(savingDto);

        Mockito.verify(repository, Mockito.times(1)).save(saving);
    }

    @Test
    public void findById() {
        StudentDto expected = new StudentDto(studentId, studentFirstName, studentLastName, groupId, groupName);
        StudentDto filledDto = new StudentDto(studentId, studentFirstName, studentLastName, groupId, groupName);
        Student found = new Student(studentId, studentFirstName, studentLastName, new Group(groupId));

        Mockito.when(repository.findById(studentId)).thenReturn(Optional.ofNullable(found));
        Mockito.when(mapper.studentToStudentDto(found)).thenReturn(filledDto);

        StudentDto actual = service.findById(studentId);

        Mockito.verify(repository, Mockito.times(1)).findById(studentId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        int student1Id = 1;
        String student1FirstName = "Ivan";
        String student1LastName = "Ivanov";
        int student2Id = 3;
        String student2FirstName = "Petr";
        String student2LastName = "Petrov";
        int groupId = 2;
        String groupName = "group";

        List<StudentDto> expected = Arrays.asList(
                new StudentDto(student1Id, student1FirstName, student1LastName, groupId, groupName),
                new StudentDto(student2Id, student2FirstName, student2LastName, groupId, groupName)
        );

        List<StudentDto> filledDto = Arrays.asList(
                new StudentDto(student1Id, student1FirstName, student1LastName, groupId, groupName),
                new StudentDto(student2Id, student2FirstName, student2LastName, groupId, groupName)
        );

        List<Student> found = Arrays.asList(
                new Student(student1Id, student1FirstName, student1LastName, new Group(groupId)),
                new Student(student2Id, student2FirstName, student2LastName, new Group(groupId))
        );

        List<Student> filled = Arrays.asList(
                new Student(student1Id, student1FirstName, student1LastName, new Group(groupId, groupName)),
                new Student(student2Id, student2FirstName, student2LastName, new Group(groupId, groupName))
        );

        Mockito.when(repository.findAll()).thenReturn(found);
        Mockito.when(mapper.studentToStudentDto(filled)).thenReturn(filledDto);

        List<StudentDto> actual = service.findAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        StudentDto updatingDto = new StudentDto(studentId, studentFirstName, studentLastName, groupId, groupName);
        Student updating = new Student(studentId, studentFirstName, studentLastName, new Group(groupId, groupName));

        Mockito.when(mapper.studentDtoToStudent(updatingDto)).thenReturn(updating);

        service.update(updatingDto);

        Mockito.verify(repository, Mockito.times(1)).save(updating);
    }

    @Test
    public void delete() {
        service.delete(new StudentDto(studentId));

        Mockito.verify(repository, Mockito.times(1)).delete(new Student(studentId));
    }

    @Test
    public void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(studentId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(studentId));
        Assertions.assertEquals(String.format("student with id %s not found", studentId), throwable.getMessage());
    }

    @Test
    public void getFilteredStudents() {
        Mockito.when(studentSpecification.filterStudents(any(StudentFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredStudents(new StudentFilter());

        Mockito.verify(studentSpecification, Mockito.times(1)). filterStudents(any(StudentFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).studentToStudentDto(any(List.class));
    }
}
