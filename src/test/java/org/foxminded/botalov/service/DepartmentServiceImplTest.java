package org.foxminded.botalov.service;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.foxminded.botalov.repository.DepartmentRepository;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentFilter;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.DepartmentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class DepartmentServiceImplTest {
    private final DepartmentRepository repository = Mockito.mock(DepartmentRepository.class);
    private final DepartmentMapper mapper = Mockito.mock(DepartmentMapper.class);
    private final DepartmentSpecification departmentSpecification = Mockito.mock(DepartmentSpecification.class);
    private final DepartmentService service = new DepartmentServiceImpl(repository, mapper, departmentSpecification);

    private final int departmentId = 1;
    private final String departmentName = "department";

    @Test
    public void save() {
        Mockito.when(mapper.departmentDtoToDepartment(new DepartmentDto(departmentName)))
                .thenReturn(new Department(departmentName));

        Mockito.when(mapper.departmentToDepartmentDto(new Department(departmentId, departmentName)))
                .thenReturn(new DepartmentDto(departmentId, departmentName));

        service.save(new DepartmentDto(departmentName));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Department(departmentName));

        Mockito.verify(mapper, Mockito.times(1))
                .departmentDtoToDepartment(new DepartmentDto(departmentName));
    }

    @Test
    public void findById() {
        Mockito.when(mapper.departmentToDepartmentDto(new Department(departmentId, departmentName)))
                .thenReturn(new DepartmentDto(departmentId, departmentName));

        Mockito.when(repository.findById(departmentId))
                .thenReturn(Optional.ofNullable(new Department(departmentId, departmentName)));

        DepartmentDto actual = service.findById(departmentId);

        Mockito.verify(mapper, Mockito.times(1))
                .departmentToDepartmentDto(new Department(departmentId, departmentName));

        Mockito.verify(repository, Mockito.times(1)).findById(departmentId);
        Assertions.assertEquals(new DepartmentDto(departmentId, departmentName), actual);
    }

    @Test
    public void findAll() {
        int department1Id = 1;
        int department2Id = 2;
        List<DepartmentDto> expected = Arrays.asList(new DepartmentDto(department1Id), new DepartmentDto(department2Id));
        List<Department> returned = Arrays.asList(new Department(department1Id), new Department(department2Id));
        List<DepartmentDto> mapped = Arrays.asList(new DepartmentDto(department1Id), new DepartmentDto(department2Id));

        Mockito.when(mapper.departmentToDepartmentDto(returned)).thenReturn(mapped);
        Mockito.when(repository.findAll()).thenReturn(returned);

        List<DepartmentDto> actual = service.findAll();

        Mockito.verify(mapper, Mockito.times(1)).departmentToDepartmentDto(returned);
        Mockito.verify(repository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Mockito.when(mapper.departmentDtoToDepartment(new DepartmentDto(departmentId, departmentName)))
                .thenReturn(new Department(departmentId, departmentName));

        service.update(new DepartmentDto(departmentId, departmentName));

        Mockito.verify(repository, Mockito.times(1))
                .save(new Department(departmentId, departmentName));

        Mockito.verify(mapper, Mockito.times(1))
                .departmentDtoToDepartment(new DepartmentDto(departmentId, departmentName));
    }

    @Test
    public void delete() {
        Mockito.when(mapper.departmentDtoToDepartment(new DepartmentDto(departmentId, departmentName)))
                .thenReturn(new Department(departmentId, departmentName));

        service.delete(new DepartmentDto(departmentId, departmentName));

        Mockito.verify(repository, Mockito.times(1))
                .delete(new Department(departmentId, departmentName));

        Mockito.verify(mapper, Mockito.times(1))
                .departmentDtoToDepartment(new DepartmentDto(departmentId, departmentName));
    }

    @Test
    public  void findByIdShouldThrowExceptionIfIdNotExist() {
        Mockito.when(repository.findById(departmentId)).thenReturn(Optional.ofNullable(null));

        Throwable throwable = Assertions.assertThrows(EntityNotFoundException.class, () -> service.findById(departmentId));
        Assertions.assertEquals(String.format("department with id %s not found", departmentId), throwable.getMessage());
    }

    @Test
    public void getFilteredDepartments() {
        Mockito.when(departmentSpecification.filterDepartments(any(DepartmentFilter.class))).thenReturn(Specification.where(null));

        service.getFilteredDepartments(new DepartmentFilter());

        Mockito.verify(departmentSpecification, Mockito.times(1)). filterDepartments(any(DepartmentFilter.class));
        Mockito.verify(repository, Mockito.times(1)).findAll(Specification.where(null));
        Mockito.verify(mapper, Mockito.times(1)).departmentToDepartmentDto(any(List.class));
    }
}
