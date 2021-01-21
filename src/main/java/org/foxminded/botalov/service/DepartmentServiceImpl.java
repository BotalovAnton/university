package org.foxminded.botalov.service;

import org.foxminded.botalov.model.department.Department;
import org.foxminded.botalov.model.department.DepartmentDto;
import org.foxminded.botalov.repository.DepartmentRepository;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentFilter;
import org.foxminded.botalov.repository.specification.fordepartment.DepartmentSpecification;
import org.foxminded.botalov.service.exception.EntityNotFoundException;
import org.foxminded.botalov.utilities.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final DepartmentSpecification departmentSpecification;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository,
                                 DepartmentMapper mapper,
                                 DepartmentSpecification departmentSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.departmentSpecification = departmentSpecification;
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = mapper.departmentDtoToDepartment(departmentDto);

        department = repository.save(department);

        return mapper.departmentToDepartmentDto(department);
    }

    @Override
    public DepartmentDto findById(Integer id) {
        Department department = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("department with id %s not found", id)));

        return mapper.departmentToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> findAll() {
        List<Department> departments = repository.findAll();

        departments.sort(Comparator.comparing(Department::getId));

        return mapper.departmentToDepartmentDto(departments);
    }

    @Override
    public void update(DepartmentDto departmentDto) {

        repository.save(mapper.departmentDtoToDepartment(departmentDto));
    }

    @Override
    public void delete(DepartmentDto departmentDto) {
        repository.delete(mapper.departmentDtoToDepartment(departmentDto));
    }

    @Override
    public List<DepartmentDto> getFilteredDepartments(DepartmentFilter filter) {
        Specification<Department> specification = departmentSpecification.filterDepartments(filter);

        return mapper.departmentToDepartmentDto(repository.findAll(specification));
    }
}
